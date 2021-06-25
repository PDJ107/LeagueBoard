package controller;

import annotation.Auth;
import domain.TestUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.TestService;
import util.JwtUtil;

@Controller
public class TestController {


    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TestService testService;

    @ResponseBody
    @RequestMapping(value = "/mybatis", method = RequestMethod.GET)
    public ResponseEntity mybatisTest(){
        return new ResponseEntity( testService.test(), HttpStatus.OK);
    }

    // 토큰이 없어도 사용가능한 api ( 비로그인 )
    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity test(){
        return new ResponseEntity("non login api", HttpStatus.OK);
    }

    // 토큰이 있어야만 사용가능한 api ( 로그인 )
    @Auth
    @ResponseBody
    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public ResponseEntity testToken(){
        return new ResponseEntity("login api", HttpStatus.OK);
    }

    // 토큰을 발급하는 api
    @ResponseBody
    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public ResponseEntity test3(){
        return new ResponseEntity(jwtUtil.genJsonWebToken(Long.valueOf(1)), HttpStatus.OK);
    }

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
