package serviceImpl;

import domain.League;
import domain.Summoner;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import service.RiotApiService;

import java.util.ArrayList;
import java.util.List;

@Service
public class RiotApiServiceImpl implements RiotApiService {
    @Value("${riot.development.api.key}")
    private static String api_key;

    public Summoner getSummonerInfo(String summoner_name) throws Exception{
        // 한글 닉네임은 띄어쓰기까지 정확히 입력해야함
        summoner_name = summoner_name.replace(" ", "%20");
        String url = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summoner_name;

        HttpGet getRequest = new HttpGet(url); //GET 메소드 URL 생성

        //getRequest.addHeader("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
        //getRequest.addHeader("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8");
        //getRequest.addHeader("Origin", "https://developer.riotgames.com");

        getRequest.addHeader("X-Riot-Token", api_key);

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(getRequest);

        //Response 출력
        if (response.getStatusLine().getStatusCode() == 200) {
            //String json_string = EntityUtils.toString(response.getEntity());
            //JSONObject temp1 = new JSONObject(json_string);

            String json = EntityUtils.toString(response.getEntity(), "UTF-8");
            //System.out.println(json);

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(json);

            Summoner summoner = new Summoner();
            summoner.setEncrypted_name((String)jsonObject.get("id"));
            summoner.setPuuid((String)jsonObject.get("puuid"));
            summoner.setProfile_icon_id((Long)jsonObject.get("profileIconId"));
            summoner.setLevel((Long)jsonObject.get("summonerLevel"));

            //ObjectMapper mapper = new ObjectMapper();
            //Summoner summoner = mapper.readValue(json, Summoner.class);
            return summoner;
        }
        // exception 처리
        else {
            System.out.println("response is error : " + response.getStatusLine().getStatusCode());
        }

        return new Summoner();
    }
    public List<League> getLeagueInfo(String encrypted_summoner_name) throws Exception{
        List<League> league_list = new ArrayList<League>();

        String url = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/"
                + encrypted_summoner_name;

        HttpGet getRequest = new HttpGet(url); //GET 메소드 URL 생성

        getRequest.addHeader("X-Riot-Token", api_key);

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(getRequest);

        //Response 출력
        if (response.getStatusLine().getStatusCode() == 200) {
            String json = EntityUtils.toString(response.getEntity(), "UTF-8");
            //System.out.println(json);

            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(json);

            for(int i = 0; i < jsonArray.size(); ++i) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                League league = new League();

                league.setQueue_type((String)jsonObject.get("queueType"));
                league.setTier((String)jsonObject.get("tier"));
                league.setRank((String)jsonObject.get("rank"));
                league.setPoint((Long)jsonObject.get("leaguePoints"));
                league.setWins((Long)jsonObject.get("wins"));
                league.setLosses((Long)jsonObject.get("losses"));

                league_list.add(league);
            }
        }
        // exception 처리
        else {
            System.out.println("response is error : " + response.getStatusLine().getStatusCode());
        }

        return league_list;
    }
    public Boolean isPlaying(String encrypted_summoner_name) throws Exception {
        String url = "https://kr.api.riotgames.com/lol/spectator/v4/active-games/by-summoner/"
                + encrypted_summoner_name;

        HttpGet getRequest = new HttpGet(url); //GET 메소드 URL 생성

        getRequest.addHeader("X-Riot-Token", api_key);

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(getRequest);

        //Response 출력
        if (response.getStatusLine().getStatusCode() == 200) {
            return true;
        }
        // exception 처리
        else {
            return false;
        }
    }
}
