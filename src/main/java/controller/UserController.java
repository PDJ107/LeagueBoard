package controller;

import annotation.Auth;
import domain.Report;
import domain.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "모든 유저 정보 조회", notes = "모든 유저의 정보를 조회합니다. 소환사 정보와 리그 정보도 포합됩니다.")
    public ResponseEntity getUserList() throws Exception {
        return new ResponseEntity(userService.getUserList(), HttpStatus.OK);
    }

    // 내 정보 가져오기 (토큰 체크)
    @Auth
    @ResponseBody
    @RequestMapping(value = "/myinfo", method = RequestMethod.GET)
    @ApiOperation(value = "내 정보 조회", notes = "자신의 정보를 조회합니다.")
    public ResponseEntity getUser() throws Exception{
        return new ResponseEntity(userService.getUser(), HttpStatus.OK);
    }

    // 내 정보 가져오기 (토큰 체크) (소환사, 리그 정보)
    @Auth
    @ResponseBody
    @RequestMapping(value = "/myinfo2", method = RequestMethod.GET)
    @ApiOperation(value = "내 정보 조회2", notes = "자신의 정보를 조회합니다. 소한사 정보와 리그 정보도 포함됩니다.")
    public ResponseEntity getUserInfo() throws Exception{
        return new ResponseEntity(userService.getUserInfo(), HttpStatus.OK);
    }

    // 다른 유저 정보 가져오기
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "유저 정보 조회", notes = "다른 유저의 정보를 조회합니다. 소환사 정보와 리그 정보도 포합됩니다.")
    public ResponseEntity getUserInfo(@PathVariable Long id) throws Exception{
        return new ResponseEntity(userService.getUserInfoById(id), HttpStatus.OK);
    }

    // 유저 정보 업데이트
    @Auth
    @ResponseBody
    @RequestMapping(value = "/myinfo", method = RequestMethod.PUT)
    @ApiOperation(value = "내 정보 수정", notes = "자신의 정보를 수정합니다. account, password, summoner_name 을 수정할 수 있습니다.")
    public ResponseEntity updateUser(@RequestBody User user) throws Exception{
        userService.updateUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 특정 유저 정보 갱신 (소환사, 리그정보)
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "유저 정보 갱신", notes = "특정 유저의 소환사, 리그 정보를 갱신합니다.")
    public ResponseEntity updateUserInfo(@PathVariable Long id) throws Exception{
        userService.updateUserInfo(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 로그인 (user 확인 후 토큰 발급)
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "로그인", notes = "account 와 password 를 검사하고 토큰을 반환합니다.")
    public ResponseEntity checkUser(@RequestBody User user) throws Exception{
        return new ResponseEntity(userService.loginUser(user), HttpStatus.OK); // 클라이언트 응답 메소드
    }

    // 로그아웃
    /*@Auth
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.PUT)
    @ApiOperation(value = "로그아웃", notes = "토큰을 만료시킵니다.")
    public ResponseEntity logoutUser() throws Exception{
        userService.logoutUser();
        return new ResponseEntity(HttpStatus.OK);
    }*/

    // 회원가입 (user를 db에 추가한 후 토큰 발급)
    @ResponseBody
    @RequestMapping(value = "signup", method = RequestMethod.POST)
    @ApiOperation(value = "회원가입", notes = "유저를 등록합니다. 이때 소환사 이름이 실제로 존재해야 합니다.")
    public ResponseEntity addUser(@RequestBody User user) throws Exception{
        return new ResponseEntity(userService.addUser(user), HttpStatus.OK); // 클라이언트 응답 메소드
    }

    // 회원탈퇴 (유저 정보 삭제)
    @Auth
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @ApiOperation(value = "회원탈퇴", notes = "회원을 탈퇴합니다. 자신의 모든 정보가 삭제됩니다.")
    public ResponseEntity deleteUser() throws Exception{
        userService.deleteUser();
        return new ResponseEntity(HttpStatus.OK);
    }

    // 유저 신고
    @Auth
    @ResponseBody
    @RequestMapping(value = "/report", method = RequestMethod.POST)
    @ApiOperation(value = "유저 신고", notes = "특정 유저를 신고합니다. perpetrator_id (가해자 id) 와 code (신고 사유) 가 필요합니다.")
    public ResponseEntity reportUser(@RequestBody Report reportData) throws Exception {
        userService.reportUser(reportData);
        return new ResponseEntity(HttpStatus.OK);
    }
}