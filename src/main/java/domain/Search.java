package domain;

public class Search {
    protected Boolean score;
    protected Boolean empty;
    protected Integer tierScore;

    public Search(Boolean score, Boolean empty) {
        this.score = score;
        this.empty = empty;
    }

    public Integer getTierScore() {
        return tierScore;
    }

    public void setTierScore(Integer tierScore) {
        this.tierScore = tierScore;
    }

    public Boolean getScore() {
        return score;
    }

    public void setScore(Boolean score) {
        this.score = score;
    }

    public Boolean getEmpty() {
        return empty;
    }

    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }
}
