package controller;

import annotation.Auth;
import domain.TestUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.TestService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private TestService testService;

    // 내 정보 가져오기 (토큰 체크)
    @Auth
    @RequestMapping(value = "myinfo", method = RequestMethod.GET)
    public ResponseEntity getUser() throws Exception{
        return new ResponseEntity(testService.getUser(), HttpStatus.OK);
    }

    // 로그인 (user 확인 후 토큰 발급)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity checkUser(@RequestBody TestUsers user) throws Exception{
        return new ResponseEntity(testService.checkUser(user), HttpStatus.OK); // 클라이언트 응답 메소드
    }

    // 회원가입 (user를 db에 추가한 후 토큰 발급)
    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public ResponseEntity addUser(@RequestBody TestUsers user) throws Exception{
        return new ResponseEntity(testService.addUser(user), HttpStatus.OK); // 클라이언트 응답 메소드
    }



}
