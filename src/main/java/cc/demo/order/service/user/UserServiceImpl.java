package cc.demo.order.service.user;

import cc.demo.order.controller.user.dto.UserReqDto;
import cc.demo.order.infra.enums.UserStatusEnum;
import cc.demo.order.infra.exception.UserException;
import cc.demo.order.infra.util.UidUtil;
import cc.demo.order.model.User;
import cc.demo.order.repository.UserRepository;
import cc.demo.order.repository.UserRoleRepository;
import cc.demo.order.vo.UserInfoVo;
import cc.demo.order.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Transactional
    @CachePut(value = "userCache", key = "#result.uid", unless = "#result == null")
    @Override
    public User createUser(UserReqDto dto) {

        User user = User.builder()
                .uid(UidUtil.generateUid(9))
                .userName(dto.getUserName())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .userStatus(UserStatusEnum.ACTIVE.getValue())
                .createTime(new Date())
                .build();

        int count = userRepository.createUser(user);
        if (count == 0) {
            LOGGER.info("User Create Failed, user name: [{}]", dto.getUserName());
            throw new UserException(1, "USER CREATE FAILED");
        }

        UserVo u = userRepository.getUserByUsername(dto.getUserName());

        count = userRoleRepository.createUserRole(u.getId());
        if (count == 0) {
            LOGGER.info("User Create Role Failed, user name: [{}]", dto.getUserName());
            throw new UserException(2, "USER CREATE ROLE FAILED");
        }

        return user;
    }

    @Cacheable(value = "userCache", key = "#uid", unless = "#result == null")
    @Override
    public UserVo getUserByUid(String uid) {
        return userRepository.getUserByUid(uid);
    }

    @Cacheable(value = "userCache", key = "#result.uid", unless = "#result == null")
    @Override
    public UserVo getUserByName(String name) {
        return userRepository.getUserByUsername(name);
    }

    @Override
    public List<UserInfoVo> getUsers(List<Long> userIds) {
        return userRepository.getUsersById(userIds);
    }

    @CacheEvict(value = "userCache", key = "#uid")
    @Transactional
    @Override
    public void updateUser(String uid, UserReqDto dto) {
        Optional<UserVo> currentUser = Optional.ofNullable(userRepository.getUserByUid(uid));
        if (currentUser.isPresent()) {
            User user = User.builder().build();

            if (!dto.getUserName().isBlank() && !dto.getUserName().equals(currentUser.get().getUserName())) {
                user.setUserName(dto.getUserName());
            }
            if (!dto.getPassword().isBlank() && !dto.getPassword().equals(currentUser.get().getPassword())) {
                user.setPassword(dto.getPassword());
            }
            if (!dto.getEmail().isBlank() && !dto.getEmail().equals(currentUser.get().getEmail())) {
                user.setEmail(dto.getEmail());
            }

            int count = userRepository.updateUser(user);
            if (count == 0) {
                LOGGER.info("User Update Failed, user name: [{}]", dto.getUserName());
                throw new UserException(3, "USER UPDATE FAILED");
            }
        }
    }

    @CacheEvict(value = "userCache", key = "#uid")
    @Override
    public void deleteUser(String uid) {
        Optional<UserVo> user = Optional.ofNullable(userRepository.getUserByUid(uid));
        user.ifPresent(value -> userRepository.deleteUser(value.getId()));
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
