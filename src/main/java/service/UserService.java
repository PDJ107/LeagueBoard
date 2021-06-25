package service;

import domain.User;

public interface UserService {
    User getUserById(Long id) throws Exception;
    User getUserByAccount(String account) throws Exception;
    User getUserByToken() throws Exception;

    String addUser(User user) throws Exception;
    String checkUser(User user) throws Exception;
}
