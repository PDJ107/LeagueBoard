package domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"user_id"})
public class Summoner {
    protected Long id;
    protected Long user_id;
    protected String encrypted_name;
    protected String puuid;
    protected Long profile_icon_id;
    protected Long level;
    protected Integer score;

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

    public String getEncrypted_name() {
        return encrypted_name;
    }

    public void setEncrypted_name(String encrypted_name) {
        this.encrypted_name = encrypted_name;
    }

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public Long getProfile_icon_id() {
        return profile_icon_id;
    }

    public void setProfile_icon_id(Long profile_icon_id) {
        this.profile_icon_id = profile_icon_id;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
