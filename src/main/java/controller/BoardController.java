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

@Controller
@RequestMapping(value = "/board")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @Auth
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "모집 글(파티) 추가", notes = "참여중인 파티가 없을경우 모집 글을 등록합니다.")
    public ResponseEntity addBoard(@RequestBody Board board) throws Exception {
        boardService.addBoard(board);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Auth
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    @ApiOperation(value = "모집 글(파티) 수정", notes = "자신이 작성한 모집 글을 수정합니다.")
    public ResponseEntity updateBoard(@RequestBody Board board) throws Exception {
        boardService.updateBoard(board);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Auth
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @ApiOperation(value = "모집 글(파티) 삭제", notes = "자신이 작성한 모집 글을 삭제합니다. 파티의 멤버들은 모두 강퇴됩니다.")
    public ResponseEntity deleteBoard() throws Exception {
        boardService.deleteBoard();
        return new ResponseEntity(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "모든 모집 글(파티) 조회", notes = "모든 모집 글을 조회합니다. (조건)")
    public ResponseEntity getBoardList(@RequestParam(value = "score", required = false, defaultValue = "false") Boolean score,
                                              @RequestParam(value = "empty", required = false, defaultValue = "false") Boolean empty) throws Exception {
        return new ResponseEntity(boardService.getBoardList(new Search(score, empty)), HttpStatus.OK);
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
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    @ApiOperation(value = "댓글 작성", notes = "자신이 속한 모집 글에 댓글을 작성합니다.")
    public ResponseEntity addComment(@RequestBody String contents) throws Exception {
        boardService.addComment(contents);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Auth
    @ResponseBody
    @RequestMapping(value = "/comment/{comment_id}", method = RequestMethod.PUT)
    @ApiOperation(value = "댓글 수정", notes = "자신이 작성한 댓글을 수정합니다.")
    public ResponseEntity updateComment(@PathVariable Long comment_id, @RequestBody String contents) throws Exception {
        boardService.updateComment(comment_id, contents);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Auth
    @ResponseBody
    @RequestMapping(value = "/comment/{comment_id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "댓글 삭제", notes = "자신이 작성한 댓글을 삭제합니다.")
    public ResponseEntity deleteComment(@PathVariable Long comment_id) throws Exception {
        boardService.deleteComment(comment_id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/{board_id}/comment", method = RequestMethod.GET)
    @ApiOperation(value = "댓글 조회", notes = "특정 모집 글에 작성된 댓글을 조회합니다.")
    public ResponseEntity getComment(@PathVariable Long board_id) throws Exception {
        return new ResponseEntity(boardService.getComment(board_id), HttpStatus.OK);
    }


    @Auth
    @ResponseBody
    @RequestMapping(value = "/member/{member_id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "파티 멤버 강퇴", notes = "파티에서 유저를 강퇴합니다. 이때 자신이 방장인 파티에 속한 유저만 강퇴할 수 있습니다.")
    public ResponseEntity deleteUserAtParty(@PathVariable Long member_id) throws Exception {
        boardService.deleteUserAtParty(member_id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Auth
    @ResponseBody
    @RequestMapping(value = "/{board_id}", method = RequestMethod.POST)
    @ApiOperation(value = "파티 참가", notes = "특정 파티에 참가합니다. 이때 방장까지 총 5명 참가할 수 있습니다.")
    public ResponseEntity enterParty(@PathVariable Long board_id) throws Exception {
        boardService.enterParty(board_id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Auth
    @ResponseBody
    @RequestMapping(value = "/member", method = RequestMethod.DELETE)
    @ApiOperation(value = "파티 나가기", notes = "자신이 참여중인 파티에서 나갑니다. 이때 해당 파티에 참여한 상태여야 합니다.")
    public ResponseEntity exitParty() throws Exception {
        boardService.exitParty();
        return new ResponseEntity(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/{board_id}/member", method = RequestMethod.GET)
    @ApiOperation(value = "파티 멤버 조회", notes = "특정 파티 멤버를 조회합니다.")
    public ResponseEntity getPartyMember(@PathVariable Long board_id) throws Exception {
        return new ResponseEntity(boardService.getPartyMember(board_id), HttpStatus.OK);
    }
}

