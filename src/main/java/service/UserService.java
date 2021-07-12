package service;

import domain.Report;
import domain.User;
import domain.UserInfo;

import java.util.List;

public interface UserService {
    //User getUserById(Long id) throws Exception;
    //User getUserByAccount(String account) throws Exception;
    List<UserInfo> getUserList() throws Exception;
    // User 정보 반환 by token
    User getUser() throws Exception;
    UserInfo getUserInfo() throws Exception;
    UserInfo getUserInfoById(Long id) throws Exception;

    // 소환사 정보 반환 by id
    //Summoner getSummonerInfo(Long id) throws Exception;
    // 리그 정보 반환 by id
    //League getLeagueInfo(Long id) throws Exception;


    // User 추가 : 토큰 반환
    String addUser(User user) throws Exception;


    // 소환사 정보 업데이트 by id
    //Summoner updateSummonerInfo(Long id) throws Exception;
    // 리그 정보 업데이트 by id
    //League updateLeagueInfo(Long id) throws Exception;
    // User 정보 업데이트 by token

    void updateUser(User user) throws Exception;
    void updateUserInfo(Long id) throws Exception;

    // User 삭제 (관련 데이터 모두) by token
    void deleteUser() throws Exception;

    // 로그인 : 토큰 반환
    String loginUser(User user) throws Exception;

    void reportUser(Report report) throws Exception;

    Boolean checkUser(Long user_id);

    Integer getSumOfScore(List<Long> idList);
}
