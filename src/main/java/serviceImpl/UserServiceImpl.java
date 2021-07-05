package serviceImpl;

import domain.*;
import exception.ErrorCode;
import exception.DefaultException;
import exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import repository.BoardMapper;
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

    public List<UserInfo> getUserList() throws Exception {
        return userMapper.getUserList();
    }


    // User 정보 반환 by token
    public User getUser() throws Exception {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        Long user_id = jwtUtil.getIdFromToken(request.getHeader("Authorization"));
        System.out.println(request.getHeader("Authorization"));
        System.out.println(user_id);
        if(!userMapper.checkUserById(user_id)) throw new UserException(ErrorCode.Invalid_Request);
        else return userMapper.getUserById(user_id);
    }

    public UserInfo getUserInfo() throws Exception {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        Long user_id = jwtUtil.getIdFromToken(request.getHeader("Authorization"));
        if(!userMapper.checkUserById(user_id)) throw new UserException(ErrorCode.Invalid_Request);
        else return userMapper.getUserInfo(user_id);
    }

    public UserInfo getUserInfoById(Long id) throws Exception {
        if(!userMapper.checkUserById(id)) throw new UserException(ErrorCode.Invalid_Request);
        else return userMapper.getUserInfo(id);
    }


    // User 추가 : 토큰 반환
    public String addUser(User user) throws Exception {
        // account null 예외처리
        // password null 예외처리

        if(userMapper.checkUserByAccount(user.getAccount()))
            throw new UserException(ErrorCode.Invalid_Request); // account 중복

        // summoner_name 체크
        if(!riotApiService.checkSummoner(user.getSummoner_name()))
            throw new UserException(ErrorCode.Invalid_Request); // 잘못된 summoner_name

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
    public void updateUser(User user) throws Exception {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        Long user_id = jwtUtil.getIdFromToken(request.getHeader("Authorization"));
        // account 중복 체크
        if(user.getAccount() != null && userMapper.checkUserByAccount(user.getAccount()))
            throw new UserException(ErrorCode.Account_Already_Exists); // 이미 존재하는 유저

        // password 암호화
        if(user.getPassword() != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String pw = encoder.encode(user.getPassword());
            user.setPassword(pw);
        }

        if(user.getSummoner_name() != null) {
            // summoner_name 체크
            if(!riotApiService.checkSummoner(user.getSummoner_name()))
                throw new UserException(ErrorCode.Invalid_Request); // 잘못된 summoner_name
            updateUserInfo(user_id);
        }

        user.setId(user_id);

        if(!userMapper.checkUserById(user_id)) throw new UserException(ErrorCode.Invalid_Request); // 잘못된 token id
        else userMapper.updateUser(user);


    }

    // riot api 사용, summoner & League 업데이트
    public void updateUserInfo(Long id) throws Exception {
        if(!userMapper.checkUserById(id)) throw new UserException(ErrorCode.Invalid_Request);

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
        for(int i = 0; i < leagueList.size(); ++i) {
            leagueList.get(i).setUser_id(id);
            userMapper.addLeagueInfo(leagueList.get(i));
        }
    }

    // User 삭제 (관련 데이터 모두) by token
    public void deleteUser() throws Exception {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        Long user_id = jwtUtil.getIdFromToken(request.getHeader("Authorization"));

        if(!userMapper.checkUserById(user_id)) throw new UserException(ErrorCode.Invalid_Request);
        else {
            boardService.exitPartyByUserId(user_id);

            userMapper.deleteUser(user_id);

            // 토큰 로그아웃 처리
        }
    }

    // 로그인 : 토큰 반환
    public String loginUser(User user) throws Exception {
        if(!userMapper.checkUserByAccount(user.getAccount()))
            throw new UserException(ErrorCode.User_Not_Found);
        User userdata = userMapper.getUserByAccount(user.getAccount());

        // password 비교
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(user.getPassword(), userdata.getPassword()))
            throw new UserException(ErrorCode.User_Not_Found); // 잘못된 패스워드

        return jwtUtil.genJsonWebToken(userdata.getId());
    }

    public void logoutUser() throws Exception {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        Long user_id = jwtUtil.getIdFromToken(request.getHeader("Authorization"));

    }

    public void reportUser(Report report) throws Exception {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        Long user_id = jwtUtil.getIdFromToken(request.getHeader("Authorization"));

        if(!userMapper.checkUserById(user_id)) throw new UserException(ErrorCode.Invalid_Request);
        else {
            if(!userMapper.checkUserById(report.getPerpetrator_id()))
                throw new UserException(ErrorCode.User_Not_Found);
            else if(user_id == report.getPerpetrator_id())
                throw new UserException(ErrorCode.Invalid_Request); // 자신을 신고할 수 없음
            report.setVictim_id(user_id);
            userMapper.addReport(report);
        }
    }
}
