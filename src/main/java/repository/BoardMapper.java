package repository;

import domain.Board;
import domain.BoardInfo;
import domain.Comment;
import domain.Member;;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardMapper {
    // board
    boolean checkBoardByUserId(Long user_id);
    boolean checkBoardById(Long board_id);
    void addBoard(Board board);
    void updateBoard(Board board);
    void deleteBoardByUserId(Long user_id);
    List<Board> getBoardList();
    List<Board> getBoardListByScore(Integer score);
    BoardInfo getBoard(Long board_id);
    Board getBoardByUserId(Long user_id);

    // comment
    boolean checkCommentById(Long comment_id);
    void addComment(Comment comment);
    void updateComment(Comment comment);
    void deleteComment(Long comment_id);
    Comment getCommentById(Long comment_id);
    List<Comment> getCommentList(Long board_id);

    // member
    boolean checkMemberByUserId(Long user_id);
    void addMember(Member member);
    void deleteMember(Member member);
    void deleteAllMember(Long board_id);
    List<Member> getMemberList(Long board_id);
    Member getMemberByUserId(Long user_id);
    Integer getMemberNumById(Long board_id);
}
