package service;

import domain.TestUsers;

public interface TestService {
    String test();

    TestUsers getUser() throws Exception;
    String checkUser(TestUsers user) throws Exception;
    String addUser(TestUsers user) throws Exception;
}
