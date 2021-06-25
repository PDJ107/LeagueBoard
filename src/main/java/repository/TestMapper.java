package repository;

import domain.TestUsers;
import org.springframework.stereotype.Repository;

@Repository
public interface TestMapper {
    String test();

    Long getUser(TestUsers user);
    TestUsers getUserById(Long id);

    boolean checkUser(TestUsers user);
    boolean checkUserByid(Long id);
    boolean checkUserByAccount(TestUsers user);

    void addUser(TestUsers user);
}
