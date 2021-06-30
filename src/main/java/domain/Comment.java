package domain;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class Comment {
    protected Long id;
    protected Long writer_id;
    protected Long board_id;
    protected String contents;
    protected String post_time; // 나중에 타입 체크
    protected Boolean is_deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWriter_id() {
        return writer_id;
    }

    public void setWriter_id(Long writer_id) {
        this.writer_id = writer_id;
    }

    public Long getBoard_id() {
        return board_id;
    }

    public void setBoard_id(Long board_id) {
        this.board_id = board_id;
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

    public Boolean getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Boolean is_deleted) {
        this.is_deleted = is_deleted;
    }
}
