package domain;

public class Board {
    protected Long id;
    protected Long admin_id;
    protected String title;
    protected String contents;
    protected String post_time; // 나중에 타입 체크
    protected Integer mean_score;

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
