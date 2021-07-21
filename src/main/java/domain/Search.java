package domain;

import exception.ErrorCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Search {
    protected Boolean score;
    protected Boolean empty;

    //@Min(value = 1) @Max(value = 20)
    protected Integer count;

    //@Min(value = 1)
    protected Integer page;

    public Search(Boolean score, Boolean empty, Integer count, Integer page) {
        this.score = score;
        this.empty = empty;
        this.count = count;
        this.page = page;
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
