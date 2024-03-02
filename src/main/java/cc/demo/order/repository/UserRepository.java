package cc.demo.order.repository;

import cc.demo.order.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepository {

    User getUserByUid(String uid);
    int createUser(User user);
    int updateUser(User user);
    int deleteUser(String id);
    Page<User> findUserPaging(Pageable page);
}
