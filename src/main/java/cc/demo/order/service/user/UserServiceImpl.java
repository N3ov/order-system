package cc.demo.order.service.user;

import cc.demo.order.controller.user.dto.UserReqDto;
import cc.demo.order.infra.enums.UserStatusEnum;
import cc.demo.order.infra.exception.UserException;
import cc.demo.order.infra.util.UidUtil;
import cc.demo.order.model.User;
import cc.demo.order.repository.UserRepository;
import cc.demo.order.repository.UserRoleRepository;
import cc.demo.order.vo.UserInfoVo;
import cc.demo.order.vo.UserRoleVo;
import cc.demo.order.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static cc.demo.order.infra.constants.UserErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Transactional
    @Override
    public User createUser(UserReqDto dto) {

        User user = User.builder()
                .uid(UidUtil.generateUid(9))
                .userName(dto.getUserName())
                .passwd(dto.getPassword())
                .email(dto.getEmail())
                .userStatus(UserStatusEnum.ACTIVE.getValue())
                .createTime(new Date())
                .build();

        int count = userRepository.createUser(user);
        if (count == 0) {
            LOGGER.info("User Create Failed, user name: [{}]", dto.getUserName());
            throw new UserException(USER_CREATE_FAILED.getCode(), USER_CREATE_FAILED.getMessage());
        }

        UserVo u = userRepository.getUserByUsername(dto.getUserName());

        count = userRoleRepository.createUserRole(u.getId());
        if (count == 0) {
            LOGGER.info("User Create Role Failed, user name: [{}]", dto.getUserName());
            throw new UserException(USER_CREATE_ROLE_FAILED.getCode(), USER_CREATE_ROLE_FAILED.getMessage());
        }

        return user;
    }

    @Cacheable(value = "userCache", key = "#uid", unless = "#result == null")
    @Override
    public UserVo getUserByUid(String uid) {
        return userRepository.getUserByUid(uid);
    }

    @Cacheable(value = "userCache", key = "#name", unless = "#result == null")
    @Override
    public UserRoleVo getUserByName(String name) {
        return userRoleRepository.getUserRoleByName(name);
    }

    @Override
    public List<UserInfoVo> getUsers(List<Long> userIds) {
        return userRepository.getUsersById(userIds);
    }

    @Transactional
    @Override
    public void updateUser(String uid, UserReqDto dto) {
        Optional<UserVo> currentUser = Optional.ofNullable(userRepository.getUserByUid(uid));
        if (currentUser.isPresent()) {
            User user = User.builder().build();

            if (!dto.getUserName().isBlank() && !dto.getUserName().equals(currentUser.get().getUserName())) {
                user.setUserName(dto.getUserName());
            }
            if (!dto.getPassword().isBlank() && !dto.getPassword().equals(currentUser.get().getPasswd())) {
                user.setPasswd(dto.getPassword());
            }
            if (!dto.getEmail().isBlank() && !dto.getEmail().equals(currentUser.get().getEmail())) {
                user.setEmail(dto.getEmail());
            }

            int count = userRepository.updateUser(user);
            if (count == 0) {
                LOGGER.info("User Update Failed, user name: [{}]", dto.getUserName());
                throw new UserException(USER_UPDATE_FAILED.getCode(), USER_UPDATE_FAILED.getMessage());
            }
        }
    }

    @CacheEvict(value = "userCache", key = "#uid")
    @Transactional
    @Override
    public void deleteUser(String uid) {
        Optional<UserVo> user = Optional.ofNullable(userRepository.getUserByUid(uid));
        user.ifPresent(u -> {
            int count = userRoleRepository.deleteUserRole(u.getId());
            if (count == 0) {
                throw new UserException(USER_DELETE_ROLE_FAILED.getCode(), USER_DELETE_ROLE_FAILED.getMessage());
            }
            count = userRepository.deleteUser(u.getId());
            if (count == 0) {
                throw new UserException(USER_DELETE_FAILED.getCode(), USER_DELETE_FAILED.getMessage());
            }
        });
    }

    @Override
    public Page<User> findUserPaging(Pageable page) {
        try {
            return userRepository.findUserPaging(page);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


}
