package domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"user_id"})
public class League {
    protected Long id;
    protected Long user_id;
    protected String tier;
    protected String rank;
    protected Long point;
    protected Long wins;
    protected Long losses;
    protected String queue_type;

    public String getQueue_type() {
        return queue_type;
    }

    public void setQueue_type(String queue_type) {
        this.queue_type = queue_type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public Long getWins() {
        return wins;
    }

    public void setWins(Long wins) {
        this.wins = wins;
    }

    public Long getLosses() {
        return losses;
    }

    public void setLosses(Long losses) {
        this.losses = losses;
    }
}
