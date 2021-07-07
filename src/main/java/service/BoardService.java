package service;

import domain.*;

import java.util.List;

public interface BoardService {
    // 모집글(파티) 작성
    void addBoard(Board board) throws Exception; // Auth
    // 모집글(파티) 수정
    void updateBoard(Board board) throws Exception; // Auth
    // 모집글(파티) 삭제
    void deleteBoard() throws Exception; // Auth
    // 글 목록 검색
    List<Board> getBoardList() throws Exception;
    List<Board> getBoardList2(Search search) throws Exception;  // Auth
    BoardInfo getBoard(Long board_id) throws Exception;

    // 댓글 작성
    void addComment(String contents) throws Exception; // Auth
    // 댓글 수정
    void updateComment(Long comment_id, String contents) throws Exception; // Auth
    // 댓글 삭제
    void deleteComment(Long comment_id) throws Exception; // Auth
    // 댓글 조회
    List<Comment> getComment(Long board_id) throws Exception;

    // 파티 강퇴
    void deleteUserAtParty(Long target_user_id) throws Exception; // Auth
    void deleteAllUserAtParty() throws Exception;
    // 파티 참가
    void enterParty(Long board_id) throws Exception; // Auth
    // 파티 나감
    void exitParty() throws Exception; // Auth
    // 파티 조회
    List<Member> getPartyMember(Long board_id) throws Exception;
}
