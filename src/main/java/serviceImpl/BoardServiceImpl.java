package serviceImpl;

import domain.*;
import exception.BoardException;
import exception.ErrorCode;
import exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import repository.BoardMapper;
import service.BoardService;
import service.UserService;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private UserService userService;

    // 모집글(파티) 작성
    public void addBoard(Board board) throws Exception { // Auth
        if(board.getTitle() == null) throw new BoardException(ErrorCode.Title_Is_Null);
        if(board.getContents() == null) throw new BoardException(ErrorCode.Contents_Is_Null);

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        Long user_id = jwtUtil.getIdFromToken(request.getHeader("Authorization"));
        if(!userService.checkUser(user_id)) throw new UserException(ErrorCode.Invalid_Token_User_Id);
        // 예외 : 파티에 속해있는지 체크
        if(boardMapper.checkMemberByUserId(user_id) || boardMapper.checkBoardByUserId(user_id))
            throw new BoardException(ErrorCode.Party_Already_Exists); // 이미 참가중
        else {
            UserInfo userInfo = userService.getUserInfoById(user_id);

            // userInfo null error

            System.out.println(user_id);

            board.setAdmin_id(user_id);

            // mean_score 추가
            board.setMean_score(userInfo.getSummonerInfo().getScore());

            boardMapper.addBoard(board);
        }
    }
    // 모집글(파티) 수정 (title or contents)
    public void updateBoard(Board board) throws Exception { // Auth
        //
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        Long user_id = jwtUtil.getIdFromToken(request.getHeader("Authorization"));
        if(!userService.checkUser(user_id)) throw new UserException(ErrorCode.Invalid_Token_User_Id);
        if(!boardMapper.checkBoardByUserId(user_id)) throw new BoardException(ErrorCode.Board_Not_Found); // 보드가 없음
        else {
            board.setAdmin_id(user_id);
            boardMapper.updateBoard(board);
        }
    }

    public void updateBoardScore(Long board_id) throws Exception {
        if(board_id == null) throw new BoardException(ErrorCode.Board_Id_Is_Null);
        if(!boardMapper.checkBoardById(board_id)) throw new BoardException(ErrorCode.Board_Not_Found); // 보드가 없음

        BoardInfo boardInfo = getBoard(board_id);
        List<Long> idList = new ArrayList<>();
        idList.add(boardInfo.getBoard().getAdmin_id());
        for(int i = 0; i < boardInfo.getMemberList().size(); ++i) {
            idList.add(boardInfo.getMemberList().get(i).getUser_id());
        }

        Integer sum = userService.getSumOfScore(idList);

        Board board = new Board();
        board.setId(board_id);
        board.setMean_score(sum / idList.size());
        boardMapper.updateBoardScore(board);
    }

    // 모집글(파티) 삭제
    public void deleteBoard() throws Exception { // Auth
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        Long user_id = jwtUtil.getIdFromToken(request.getHeader("Authorization"));
        if(!userService.checkUser(user_id)) throw new UserException(ErrorCode.Invalid_Token_User_Id);
        if(!boardMapper.checkBoardByUserId(user_id)) throw new BoardException(ErrorCode.Board_Not_Found); // 보드가 없음
        else {
            // 모든 멤버 강퇴
            /*List<Member> userList = boardMapper.getMemberList(boardMapper.getBoardByUserId(user_id).getId());
            Member member = new Member();
            member.setBoard_id(boardMapper.getBoardByUserId(user_id).getId());
            for(int i = 0; i < userList.size(); ++i) {
                //deleteUserAtParty(userList.get(i).getId());
                member.setUser_id(userList.get(i).getUser_id());
                boardMapper.deleteMember(member);
            }*/
            boardMapper.deleteAllMember(boardMapper.getBoardByUserId(user_id).getId());
            boardMapper.deleteBoardByUserId(user_id);
        }
    }

    public List<Board> getBoardList(Search search) throws Exception {  // Auth
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        Long user_id = jwtUtil.getIdFromToken(request.getHeader("Authorization"));
        if(!userService.checkUser(user_id)) throw new UserException(ErrorCode.Invalid_Token_User_Id);
        UserInfo userInfo = userService.getUserInfoById(user_id);
        search.setTierScore(userInfo.getSummonerInfo().getScore());

        return boardMapper.getBoardList2(search);
    }

    public BoardInfo getBoard(Long board_id) throws Exception {
        if(board_id == null) throw new BoardException(ErrorCode.Board_Id_Is_Null);
        if(!boardMapper.checkBoardById(board_id))
            throw new BoardException(ErrorCode.Board_Not_Found); // 잘못된 보드 id
        BoardInfo boardInfo = boardMapper.getBoard(board_id);
        boardInfo.setMemberList(boardMapper.getMemberList(board_id));
        return boardInfo;
    }

    public BoardInfo getBoard() throws Exception {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        Long user_id = jwtUtil.getIdFromToken(request.getHeader("Authorization"));
        if(!userService.checkUser(user_id)) throw new UserException(ErrorCode.Invalid_Token_User_Id);
        Long board_id;
        if(boardMapper.checkBoardByUserId(user_id)) {
            board_id = boardMapper.getBoardByUserId(user_id).getId();
        }
        else if(boardMapper.checkMemberByUserId(user_id)) {
            board_id = boardMapper.getMemberByUserId(user_id).getBoard_id();
        }
        else
            throw new BoardException(ErrorCode.Party_Not_Exists);

        BoardInfo boardInfo = boardMapper.getBoard(board_id);
        boardInfo.setMemberList(boardMapper.getMemberList(board_id));
        return boardInfo;
    }

    // 댓글 작성
    public void addComment(String contents) throws Exception { // Auth
        if(contents == null) throw new BoardException(ErrorCode.Contents_Is_Null);
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        Long user_id = jwtUtil.getIdFromToken(request.getHeader("Authorization"));
        if(!userService.checkUser(user_id)) throw new UserException(ErrorCode.Invalid_Token_User_Id);

        Comment comment = new Comment();
        comment.setContents(contents);
        if(boardMapper.checkBoardByUserId(user_id)) {
            comment.setBoard_id(boardMapper.getBoardByUserId(user_id).getId());
        }
        else if(boardMapper.checkMemberByUserId(user_id)) {
            comment.setBoard_id(boardMapper.getMemberByUserId(user_id).getBoard_id());
        }
        else
            throw new BoardException(ErrorCode.Party_Not_Exists); // 속해있는 파티가 없음

        comment.setWriter_id(user_id);
        boardMapper.addComment(comment);
    }
    // 댓글 수정
    public void updateComment(Long comment_id, String contents) throws Exception { // Auth
        if(comment_id == null) throw new BoardException(ErrorCode.Comment_Id_Is_Null);
        if(contents == null) throw new BoardException(ErrorCode.Contents_Is_Null);
        if(!boardMapper.checkCommentById(comment_id))
            throw new BoardException(ErrorCode.Comment_Not_Found); // 잘못된 comment id

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        Long user_id = jwtUtil.getIdFromToken(request.getHeader("Authorization"));
        if(!userService.checkUser(user_id)) throw new UserException(ErrorCode.Invalid_Token_User_Id);

        Comment target_comment = boardMapper.getCommentById(comment_id);

        if(target_comment.getWriter_id() != user_id)
            throw new BoardException(ErrorCode.Comment_Invalid_Access); // 자신이 쓴 댓글이 아님
        else {
            target_comment.setContents(contents);
            boardMapper.updateComment(target_comment);
        }
    }
    // 댓글 삭제
    public void deleteComment(Long comment_id) throws Exception { // Auth
        if(comment_id == null) throw new BoardException(ErrorCode.Comment_Id_Is_Null);
        if(!boardMapper.checkCommentById(comment_id))
            throw new BoardException(ErrorCode.Comment_Not_Found); // 잘못된 id

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        Long user_id = jwtUtil.getIdFromToken(request.getHeader("Authorization"));
        if(!userService.checkUser(user_id)) throw new UserException(ErrorCode.Invalid_Token_User_Id);

        Comment target_comment = boardMapper.getCommentById(comment_id);

        if(target_comment.getWriter_id() != user_id) throw new BoardException(ErrorCode.Comment_Invalid_Access); // 자신이 쓴 댓글이 아님
        else boardMapper.deleteComment(comment_id); // soft delete
    }
    // 댓글 조회
    public List<Comment> getComment(Long board_id) throws Exception {
        if(board_id == null) throw new BoardException(ErrorCode.Board_Id_Is_Null);
        if(!boardMapper.checkBoardById(board_id))
            throw new BoardException(ErrorCode.Board_Not_Found); // 잘못된 id

        List<Comment> commentList = boardMapper.getCommentList(board_id);
        return commentList;
    }

    // 파티 강퇴
    public void deleteUserAtParty(Long target_user_id) throws Exception { // Auth
        if(target_user_id == null) throw new BoardException(ErrorCode.User_Id_Is_Null);
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        Long user_id = jwtUtil.getIdFromToken(request.getHeader("Authorization"));
        if(!userService.checkUser(user_id)) throw new UserException(ErrorCode.Invalid_Token_User_Id);

        if(!boardMapper.checkBoardByUserId(user_id))
            throw new BoardException(ErrorCode.Board_Not_Found); // 보드가 없음

        if(!boardMapper.checkMemberByUserId(target_user_id))
            throw new BoardException(ErrorCode.User_Not_Found); // 잘못된 타겟 유저 id
        else {
            Member targetMember = boardMapper.getMemberByUserId(target_user_id);
            Long board_id = boardMapper.getBoardByUserId(user_id).getId();
            if(board_id != targetMember.getBoard_id())
                throw new BoardException(ErrorCode.Member_Not_Found); // 해당 파티에 속한 멤버가 아님
            boardMapper.deleteMember(targetMember);
            updateBoardScore(board_id);
        }
    }

    // 모든 유저 강퇴
    public void deleteAllUserAtParty() throws Exception { // Auth
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        Long user_id = jwtUtil.getIdFromToken(request.getHeader("Authorization"));
        if(!userService.checkUser(user_id)) throw new UserException(ErrorCode.Invalid_Token_User_Id);

        if(!boardMapper.checkBoardByUserId(user_id)) return; // 보드가 없음
        Long board_id = boardMapper.getBoardByUserId(user_id).getId();
        boardMapper.deleteAllMember(board_id);
    }

    // 파티 참가
    @Transactional(isolation = Isolation.DEFAULT) //
    public void enterParty(Long board_id) throws Exception { // Auth
        if(board_id == null) throw new BoardException(ErrorCode.Board_Id_Is_Null);
        if(!boardMapper.checkBoardById(board_id))
            throw new BoardException(ErrorCode.Board_Not_Found); // 잘못된 id
        else if(boardMapper.getMemberNumById(board_id) >= 4)
            throw new BoardException(ErrorCode.Party_Is_Full); // 자리가 없음

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        Long user_id = jwtUtil.getIdFromToken(request.getHeader("Authorization"));
        if(!userService.checkUser(user_id)) throw new UserException(ErrorCode.Invalid_Token_User_Id);

        // 예외 : 파티에 속해있는지 체크
        if(boardMapper.checkMemberByUserId(user_id) || boardMapper.checkBoardByUserId(user_id))
            throw new BoardException(ErrorCode.Party_Already_Exists); // 이미 참가중

        Member newMember = new Member();
        newMember.setUser_id(user_id);
        newMember.setBoard_id(board_id);

        boardMapper.addMember(newMember);
        updateBoardScore(board_id);
    }

    // 파티 나감
    public void exitParty() throws Exception { // Auth
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        Long user_id = jwtUtil.getIdFromToken(request.getHeader("Authorization"));
        if(!userService.checkUser(user_id)) throw new UserException(ErrorCode.Invalid_Token_User_Id);

        if(boardMapper.checkMemberByUserId(user_id)) {// 멤버로 참가중일 경우
            Member member = new Member();
            member.setUser_id(user_id);
            member.setBoard_id(boardMapper.getMemberByUserId(user_id).getBoard_id());
            boardMapper.deleteMember(member);
            updateBoardScore(member.getBoard_id());
        }
        else if(boardMapper.checkBoardByUserId(user_id)) {// admin일 경우
            throw new BoardException(ErrorCode.Party_Invalid_Request); // admin인 경우 delete board만 가능
        }
        else
            throw new BoardException(ErrorCode.Party_Not_Exists); // 파티에 참가중이 아님
    }

    // 파티 조회
    public List<Member> getPartyMember(Long board_id) throws Exception {
        if(board_id == null) throw new BoardException(ErrorCode.Board_Id_Is_Null);
        if(!boardMapper.checkBoardById(board_id))
            throw new BoardException(ErrorCode.Board_Not_Found); // 잘못된 id

        return boardMapper.getMemberList(board_id);
    }


}
