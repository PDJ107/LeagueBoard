package controller;

import annotation.Auth;
import domain.Board;
import domain.Comment;
import domain.Search;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.BoardService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller
@RequestMapping(value = "/boards")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @Auth
    @ResponseBody
    @RequestMapping(value = "/me", method = RequestMethod.POST)
    @ApiOperation(value = "모집 글(파티) 추가", notes = "참여중인 파티가 없을경우 모집 글을 등록합니다.")
    public ResponseEntity addBoard(@RequestBody @Valid Board board) throws Exception {
        boardService.addBoard(board);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Auth
    @ResponseBody
    @RequestMapping(value = "/me", method = RequestMethod.PATCH)
    @ApiOperation(value = "모집 글(파티) 수정", notes = "자신이 작성한 모집 글을 수정합니다.")
    public ResponseEntity updateBoard(@RequestBody Board board) throws Exception {
        boardService.updateBoard(board);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Auth
    @ResponseBody
    @RequestMapping(value = "/me", method = RequestMethod.DELETE)
    @ApiOperation(value = "모집 글(파티) 삭제", notes = "자신이 작성한 모집 글을 삭제합니다. 파티의 멤버들은 모두 강퇴됩니다.")
    public ResponseEntity deleteBoard() throws Exception {
        boardService.deleteBoard();
        return new ResponseEntity(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/page/{pageNum}", method = RequestMethod.GET)
    @ApiOperation(value = "모집 글(파티) 조회 (페이지)", notes = "모집 글을 조회합니다. (조건)")
    public ResponseEntity getBoardList(@RequestParam(value = "score", required = false, defaultValue = "false") Boolean score,
                                       @RequestParam(value = "empty", required = false, defaultValue = "false") Boolean empty,
                                       @RequestParam(value = "count", required = false, defaultValue = "5") Integer count,
                                       @PathVariable Integer pageNum) throws Exception {
        return new ResponseEntity(boardService.getBoardList(new Search(score, empty, count, pageNum)), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/{board_id}", method = RequestMethod.GET)
    @ApiOperation(value = "모집 글(파티) 조회", notes = "특정 모집 글을 조회합니다.")
    public ResponseEntity getBoard(@PathVariable Long board_id) throws Exception {
        return new ResponseEntity(boardService.getBoard(board_id), HttpStatus.OK);
    }

    @Auth
    @ResponseBody
    @RequestMapping(value = "/me", method = RequestMethod.GET)
    @ApiOperation(value = "모집 글(파티) 조회", notes = "자신이 속한 파티의 정보를 조회합니다.")
    public ResponseEntity getBoard() throws Exception {
        return new ResponseEntity(boardService.getBoard(), HttpStatus.OK);
    }

    @Auth
    @ResponseBody
    @RequestMapping(value = "/me/comments", method = RequestMethod.POST)
    @ApiOperation(value = "댓글 작성", notes = "자신이 속한 모집 글에 댓글을 작성합니다.")
    public ResponseEntity addComment(@RequestBody @Valid Comment comment) throws Exception {
        boardService.addComment(comment);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Auth
    @ResponseBody
    @RequestMapping(value = "/comments/{comment_id}", method = RequestMethod.PATCH)
    @ApiOperation(value = "댓글 수정", notes = "자신이 작성한 댓글을 수정합니다.")
    public ResponseEntity updateComment(@PathVariable @NotNull Long comment_id, @RequestBody @Valid Comment comment) throws Exception {
        comment.setId(comment_id);
        boardService.updateComment(comment);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Auth
    @ResponseBody
    @RequestMapping(value = "/comments/{comment_id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "댓글 삭제", notes = "자신이 작성한 댓글을 삭제합니다.")
    public ResponseEntity deleteComment(@PathVariable Long comment_id) throws Exception {
        boardService.deleteComment(comment_id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/{board_id}/comments", method = RequestMethod.GET)
    @ApiOperation(value = "댓글 조회", notes = "특정 모집 글에 작성된 댓글을 조회합니다.")
    public ResponseEntity getComment(@PathVariable Long board_id) throws Exception {
        return new ResponseEntity(boardService.getComment(board_id), HttpStatus.OK);
    }


    @Auth
    @ResponseBody
    @RequestMapping(value = "/me/members/{member_id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "파티 멤버 강퇴", notes = "파티에서 유저를 강퇴합니다. 이때 자신이 방장인 파티에 속한 유저만 강퇴할 수 있습니다.")
    public ResponseEntity deleteUserAtParty(@PathVariable Long member_id) throws Exception {
        boardService.deleteUserAtParty(member_id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Auth
    @ResponseBody
    @RequestMapping(value = "/{board_id}/members", method = RequestMethod.POST)
    @ApiOperation(value = "파티 참가", notes = "특정 파티에 참가합니다. 이때 방장까지 총 5명 참가할 수 있습니다.")
    public ResponseEntity enterParty(@PathVariable Long board_id) throws Exception {
        boardService.enterParty(board_id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Auth
    @ResponseBody
    @RequestMapping(value = "/me/members", method = RequestMethod.DELETE)
    @ApiOperation(value = "파티 나가기", notes = "자신이 참여중인 파티에서 나갑니다. 이때 해당 파티에 참여한 상태여야 합니다.")
    public ResponseEntity exitParty() throws Exception {
        boardService.exitParty();
        return new ResponseEntity(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/{board_id}/members", method = RequestMethod.GET)
    @ApiOperation(value = "파티 멤버 조회", notes = "특정 파티 멤버를 조회합니다.")
    public ResponseEntity getPartyMember(@PathVariable Long board_id) throws Exception {
        return new ResponseEntity(boardService.getPartyMember(board_id), HttpStatus.OK);
    }
}

