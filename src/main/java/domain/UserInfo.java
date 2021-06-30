package domain;

import java.util.List;

public class UserInfo {
    protected Long id;
    protected String account;
    protected String summoner_name;

    protected Summoner summonerInfo;
    protected List<League> leagueInfo;

    public UserInfo() {
    }

    public UserInfo(User user) {
        this.id = user.getId();
        this.account = user.getAccount();
        this.summoner_name = user.getSummoner_name();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSummoner_name() {
        return summoner_name;
    }

    public void setSummoner_name(String summoner_name) {
        this.summoner_name = summoner_name;
    }

    public Summoner getSummonerInfo() {
        return summonerInfo;
    }

    public void setSummonerInfo(Summoner summonerInfo) {
        this.summonerInfo = summonerInfo;
    }

    public List<League> getLeagueInfo() {
        return leagueInfo;
    }

    public void setLeagueInfo(List<League> leagueInfo) {
        this.leagueInfo = leagueInfo;
    }
}
