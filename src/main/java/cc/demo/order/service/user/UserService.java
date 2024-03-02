package cc.demo.order.service.user;

import cc.demo.order.model.User;
import cc.demo.order.controller.user.dto.UserReqDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    User createUser(UserReqDto dto);
    User getUser(String uid);
    void updateUser(String id, UserReqDto dto);
    void deleteUser(String uid);
    Page<User> findUserPaging(Pageable pageable);
}
