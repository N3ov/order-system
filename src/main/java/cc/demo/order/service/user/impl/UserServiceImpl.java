package cc.demo.order.service.user.impl;

import cc.demo.order.controller.user.dto.UserReqDto;
import cc.demo.order.infra.enums.UserStatusEnum;
import cc.demo.order.infra.util.UidUtil;
import cc.demo.order.model.User;
import cc.demo.order.repository.UserRepository;
import cc.demo.order.service.user.UserService;
import cc.demo.order.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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

        userRepository.createUser(user);
        return user;
    }

    @Override
    public User getUser(String uid) {
        return userRepository.getUserByUid(uid);
    }

    @Override
    public List<UserVo> getUsers(List<Long> userIds) {
        return userRepository.getUsersById(userIds);
    }

    @Override
    public void updateUser(String uid, UserReqDto dto) {
        Optional<User> currentUser = Optional.ofNullable(userRepository.getUserByUid(uid));
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

            userRepository.updateUser(user);
        }
    }

    @Override
    public void deleteUser(String uid) {
        Optional<User> user = Optional.ofNullable(userRepository.getUserByUid(uid));
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
