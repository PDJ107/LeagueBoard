package util;

import domain.League;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TierScore {
    private static Map<String, Integer> score = new HashMap<String, Integer>() {
        {
            put("CHALLENGERI", 27);
            put("GRANDMASTERI", 26);
            put("MASTERI", 25);

            put("DIAMONDI", 24);
            put("DIAMONDII", 23);
            put("DIAMONDIII", 22);
            put("DIAMONDIV", 21);

            put("PLATINUMI", 20);
            put("PLATINUMII", 19);
            put("PLATINUMIII", 18);
            put("PLATINUMIV", 17);

            put("GOLDI", 16);
            put("GOLDII", 15);
            put("GOLDIII", 14);
            put("GOLDIV", 13);

            put("SILVERI", 12);
            put("SILVERII", 11);
            put("SILVERIII", 10);
            put("SILVERIV", 9);

            put("BRONZEI", 8);
            put("BRONZEII", 7);
            put("BRONZEIII", 6);
            put("BRONZEIV", 5);

            put("IRONI", 4);
            put("IRONII", 3);
            put("IRONIII", 2);
            put("IRONIV", 1);
        }
    };

    public Integer leagueScore(List<League> leagueList) {
        if(leagueList.size() == 0) return 0;
        String tr = leagueList.get(0).getTier() + leagueList.get(0).getRank();

        if(leagueList.size() > 1 && leagueList.get(1).getQueue_type().equals("RANKED_FLEX_SR"))
            tr = leagueList.get(1).getTier() + leagueList.get(1).getRank();

        System.out.println(tr);
        return score.get(tr);
    }
}
