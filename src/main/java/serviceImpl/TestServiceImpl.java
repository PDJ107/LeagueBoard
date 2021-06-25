package serviceImpl;

import domain.TestUsers;
import exception.ErrorCode;
import exception.TestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import repository.TestMapper;
import service.TestService;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestMapper testMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String test(){
        return testMapper.test();
    }

    @Override
    public TestUsers getUser() throws Exception {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        Long id = jwtUtil.getIdFromToken(request.getHeader("Authorization"));
        if(!testMapper.checkUserByid(id)) throw new TestException(ErrorCode.User_Not_Found);
        else {
            return testMapper.getUserById(id);
        }
    }

    @Override
    public String checkUser(TestUsers user) throws Exception{
        if(!testMapper.checkUser(user)) throw new TestException(ErrorCode.Invalid_Request);
        else {
            return jwtUtil.genJsonWebToken(testMapper.getUser(user));
        }
    }

    @Override
    public String addUser(TestUsers user) throws Exception{
        if(testMapper.checkUserByAccount(user)) throw new TestException(ErrorCode.Account_Already_Exists);
        else {
            testMapper.addUser(user);
            return jwtUtil.genJsonWebToken(testMapper.getUser(user));
        }
    }
}
