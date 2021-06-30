package domain;

import java.util.List;

public class BoardInfo {
    protected Board board;
    protected List<Comment> commentList;
    protected List<Member> memberList;

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public List<Member> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }
}
