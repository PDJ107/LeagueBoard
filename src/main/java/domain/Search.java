package domain;

public class Search {
    protected Boolean score;
    protected Boolean empty;
    protected Integer count;
    protected Integer page;
    protected Integer start;
    protected Integer tierScore;

    public Search(Boolean score, Boolean empty, Integer count, Integer page) {
        this.score = score;
        this.empty = empty;
        this.count = count;
        this.page = page;
        start = (page-1) * count;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
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
