package domain;

public class Party {
    protected Long id;
    protected Long admin_id;
    protected Long board_id;
    protected Long member_num;
    protected Long mean_score;
    protected Long max_score;

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

    public Long getBoard_id() {
        return board_id;
    }

    public void setBoard_id(Long board_id) {
        this.board_id = board_id;
    }

    public Long getMember_num() {
        return member_num;
    }

    public void setMember_num(Long member_num) {
        this.member_num = member_num;
    }

    public Long getMean_score() {
        return mean_score;
    }

    public void setMean_score(Long mean_score) {
        this.mean_score = mean_score;
    }

    public Long getMax_score() {
        return max_score;
    }

    public void setMax_score(Long max_score) {
        this.max_score = max_score;
    }
}
