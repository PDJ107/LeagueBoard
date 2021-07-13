package repository;


import domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    List<UserInfo> getUserList();
    User getUserById(Long id);
    User getUserByAccount(String account);
    UserInfo getUserInfo(Long id);
    Integer getSumOfScore(List<Long> idList);

    List<ReportCode> getReportCodes();
    boolean isReported(Report report);
    void updateReportNum(Long perpetrator_id);

    boolean checkUserById(Long id);
    boolean checkUserByAccount(String account);
    boolean checkSummonerInfo(Long user_id);

    void addUser(User user);
    void addSummonerInfo(Summoner summoner);
    void addLeagueInfo(List<League> leagueList);

    void deleteUser(Long id);
    void deleteLeagueInfo(Long user_id);

    void updateUser(User user);
    void updateSummonerInfo(Summoner summoner);

    void addReport(Report report);
    void countReportNum(Report report);
}
