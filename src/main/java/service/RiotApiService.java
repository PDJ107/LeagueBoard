package service;

import domain.League;
import domain.Summoner;

import java.util.List;

public interface RiotApiService {
    Boolean checkSummoner(String summoner_name) throws Exception;
    Summoner getSummonerInfo(String summoner_name) throws Exception;
    List<League> getLeagueInfo(String encrypted_summoner_name) throws Exception;
    Boolean isPlaying(String encrypted_summoner_name) throws Exception;
}
