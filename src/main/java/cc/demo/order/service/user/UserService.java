package cc.demo.order.service.user;

import cc.demo.order.controller.user.dto.UserReqDto;
import cc.demo.order.model.User;
import cc.demo.order.vo.UserVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    User createUser(UserReqDto dto);

    UserVo getUser(String uid);

    List<UserVo> getUsers(List<Long> userIds);

    void updateUser(String id, UserReqDto dto);

    void deleteUser(String uid);

    Page<User> findUserPaging(Pageable pageable);
}
