package cc.demo.order.repository;

import cc.demo.order.model.User;
import cc.demo.order.vo.UserVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepository {

    User getUserByUid(String uid);
    List<UserVo> getUsersById(List<Long> userIds);
    int createUser(User user);
    int updateUser(User user);
    int deleteUser(Long id);
    Page<User> findUserPaging(Pageable page);
}
