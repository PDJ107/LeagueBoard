package domain;

import exception.BoardException;
import exception.ErrorCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Board {
    protected Long id;
    protected Long admin_id;

    @NotNull(message = "title이 Null 입니다.")
    @Size(min = 1, max = 20, message = "title은 최소값 1과(와) 최대값 20 사이의 크기이어야 합니다.")
    protected String title; // 1 ~ 20

    @NotNull(message = "contents가 Null 입니다.")
    @Size(min = 1, max = 150, message = "contents는 최소값 1과(와) 최대값 150 사이의 크기이어야 합니다.")
    protected String contents; // 1 ~ 150

    protected String post_time; // 나중에 타입 체크
    protected Integer mean_score;
    protected Integer member_num;

    public Integer getMember_num() {
        return member_num;
    }

    public void setMember_num(Integer member_num) {
        this.member_num = member_num;
    }

    public Integer getMean_score() {
        return mean_score;
    }

    public void setMean_score(Integer mean_score) {
        this.mean_score = mean_score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(Long admin_id) {
        this.admin_id = admin_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getPost_time() {
        return post_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }
}
