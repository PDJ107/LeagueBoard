package serviceImpl;

import domain.*;
import exception.ErrorCode;
import exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import repository.UserMapper;
import service.BoardService;
import service.RiotApiService;
import service.UserService;
import util.JwtUtil;
import util.TierScore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RiotApiService riotApiService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private TierScore tierScore;

    /*public List<UserInfo> getUserList() throws Exception {
        return userMapper.getUserList();
    }*/
    
    // User 정보 반환 by token
    @Override
    public User getUser() throws Exception {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        Long user_id = jwtUtil.getIdFromToken(request.getHeader("Authorization"));

        if(!userMapper.checkUserById(user_id)) throw new UserException(ErrorCode.Invalid_Token_User_Id);
        else return userMapper.getUserById(user_id);
    }

    @Override
    public UserInfo getUserInfo() throws Exception {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        Long user_id = jwtUtil.getIdFromToken(request.getHeader("Authorization"));
        if(!userMapper.checkUserById(user_id)) throw new UserException(ErrorCode.Invalid_Token_User_Id);
        else return userMapper.getUserInfo(user_id);
    }

    @Override
    public UserInfo getUserInfoById(Long id) throws Exception {
        if(id == null) throw new UserException(ErrorCode.User_Id_Is_Null);
        if(!userMapper.checkUserById(id)) throw new UserException(ErrorCode.User_Not_Found);
        else return userMapper.getUserInfo(id);
    }


    // User 추가 : 토큰 반환
    @Override
    @Transactional
    public String addUser(User user) throws Exception {
        // 소환사이름 예외처리
        if(user.getSummoner_name() == null) throw new UserException(ErrorCode.Summoner_Name_Is_Null);
        else if(user.getSummoner_name().length() > 20)
            throw new UserException(ErrorCode.Summoner_Name_Not_Valid);

        if(userMapper.checkUserByAccount(user.getAccount()))
            throw new UserException(ErrorCode.Account_Already_Exists); // account 중복

        // summoner_name 체크
        if(!riotApiService.checkSummoner(user.getSummoner_name()))
            throw new UserException(ErrorCode.Summoner_Not_Found); // 잘못된 summoner_name

        // password 암호화
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pw = encoder.encode(user.getPassword());
        user.setPassword(pw);
        userMapper.addUser(user);

        Long user_id = userMapper.getUserByAccount(user.getAccount()).getId();

        updateUserInfo(user_id);

        return jwtUtil.genJsonWebToken(user_id);
    }

    // User 정보 업데이트 by token
    @Override
    @Transactional
    public void updateUser(User user) throws Exception {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        Long user_id = jwtUtil.getIdFromToken(request.getHeader("Authorization"));

        if(!userMapper.checkUserById(user_id)) throw new UserException(ErrorCode.Invalid_Token_User_Id);

        // account 체크
        if(user.getAccount() != null) {
            if(user.getAccount().length() < 1 || user.getAccount().length() > 20)
                throw new UserException(ErrorCode.Account_Not_Valid);
            if(userMapper.checkUserByAccount(user.getAccount()))
                throw new UserException(ErrorCode.Account_Already_Exists); // 이미 존재하는 유저
        }

        // password 암호화
        if(user.getPassword() != null) {
            if(user.getPassword().length() < 4 || user.getPassword().length() > 20)
                throw new UserException(ErrorCode.Password_Not_Valid);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String pw = encoder.encode(user.getPassword());
            user.setPassword(pw);
        }

        if(user.getSummoner_name() != null) {
            // summoner_name 체크
            if(user.getSummoner_name().length() > 20)
                throw new UserException(ErrorCode.Summoner_Name_Not_Valid);
            if(!riotApiService.checkSummoner(user.getSummoner_name()))
                throw new UserException(ErrorCode.Summoner_Not_Found); // 잘못된 summoner_name
            updateUserInfo(user_id);
        }

        user.setId(user_id);

        userMapper.updateUser(user);
    }

    // riot api 사용, summoner & League 업데이트
    @Override
    @Transactional
    public void updateUserInfo(Long id) throws Exception {
        if(id == null) throw new UserException(ErrorCode.User_Id_Is_Null);
        if(!userMapper.checkUserById(id)) throw new UserException(ErrorCode.User_Not_Found);

        User user = userMapper.getUserById(id);

        // riot api
        Summoner summoner = riotApiService.getSummonerInfo(user.getSummoner_name());

        List<League> leagueList = riotApiService.getLeagueInfo(summoner.getEncrypted_name());

        // update summonerInfo
        summoner.setUser_id(id);

        summoner.setScore(tierScore.leagueScore(leagueList));

        if(userMapper.checkSummonerInfo(id)) userMapper.updateSummonerInfo(summoner);
        else userMapper.addSummonerInfo(summoner);

        // update leagueInfo
        userMapper.deleteLeagueInfo(id);
        if(leagueList.size() > 0) {
            for (int i = 0; i < leagueList.size(); ++i) {
                leagueList.get(i).setUser_id(id);
            }
            userMapper.addLeagueInfo(leagueList);
        }
    }

    // User 삭제 (관련 데이터 모두) by token
    @Override
    @Transactional
    public void deleteUser() throws Exception {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        Long user_id = jwtUtil.getIdFromToken(request.getHeader("Authorization"));

        if(!userMapper.checkUserById(user_id)) throw new UserException(ErrorCode.Invalid_Token_User_Id);
        else {
            boardService.deleteAllUserAtParty();
            userMapper.deleteUser(user_id);

            // 토큰 로그아웃 처리
            //jwtUtil.logoutToken(request.getHeader("Authorization"));
        }
    }

    // 로그인 : 토큰 반환
    @Override
    public String loginUser(User user) throws Exception {
        if(!userMapper.checkUserByAccount(user.getAccount()))
            throw new UserException(ErrorCode.User_Invalid_Request);
        User userdata = userMapper.getUserByAccount(user.getAccount());

        // password 비교
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(user.getPassword(), userdata.getPassword()))
            throw new UserException(ErrorCode.User_Invalid_Request); // 잘못된 패스워드

        return jwtUtil.genJsonWebToken(userdata.getId());
    }

    @Override
    @Transactional
    public void reportUser(Report report) throws Exception {
        List<ReportCode> reportCodes = userMapper.getReportCodes();

        Boolean isValid = false;
        for(int i = 0; i < reportCodes.size(); ++i)
            if(report.getCode() == reportCodes.get(i).getCode()) isValid = true;
        if(!isValid) throw new UserException(ErrorCode.Report_Code_Not_Valid);

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        Long user_id = jwtUtil.getIdFromToken(request.getHeader("Authorization"));

        if(!userMapper.checkUserById(user_id)) throw new UserException(ErrorCode.Invalid_Token_User_Id);
        else {
            report.setVictim_id(user_id);

            if(!userMapper.checkUserById(report.getPerpetrator_id()))
                throw new UserException(ErrorCode.User_Not_Found);
            else if(user_id == report.getPerpetrator_id())
                throw new UserException(ErrorCode.Report_Invalid_Request); // 자신을 신고할 수 없음
            else if(userMapper.isReported(report))
                throw new UserException(ErrorCode.Report_Same_User); // 같은 유저를 2번 신고할 수 없음

            userMapper.addReport(report);
            userMapper.updateReportNum(report.getPerpetrator_id());
        }
    }

    public List<ReportCode> getReportCodes() {
        return userMapper.getReportCodes();
    }
    public Boolean checkUser(Long user_id) {
        return userMapper.checkUserById(user_id);
    }
    public Integer getSumOfScore(List<Long> idList) {
        return userMapper.getSumOfScore(idList);
    }
}
