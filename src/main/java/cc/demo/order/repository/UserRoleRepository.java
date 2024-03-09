package cc.demo.order.repository;

import cc.demo.order.vo.UserRoleVo;

public interface UserRoleRepository {

    int createUserRole(Long userId);

    UserRoleVo getUserRoleByName(String userName);

    int deleteUserRole(Long userId);
}
