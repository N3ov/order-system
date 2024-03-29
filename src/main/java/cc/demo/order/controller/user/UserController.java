package cc.demo.order.controller.user;

import cc.demo.order.controller.user.dto.UserReqDto;
import cc.demo.order.controller.user.dto.UserRespDto;
import cc.demo.order.infra.page.Pagination;
import cc.demo.order.infra.response.ResponseDto;
import cc.demo.order.model.User;
import cc.demo.order.service.user.UserService;
import cc.demo.order.vo.UserVo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseDto<UserRespDto> create(@Valid @RequestBody UserReqDto userDto) {
        User user = userService.createUser(userDto);
        return ResponseDto.success(
                UserRespDto.builder()
                        .userName(user.getUserName())
                        .uid(user.getUid())
                        .createTime(user.getCreateTime())
                        .userStatus(user.getUserStatus())
                        .build());
    }

    @PutMapping("/update")
    public ResponseDto<?> update(@RequestParam String uid, UserReqDto dto) {
        userService.updateUser(uid, dto);
        return ResponseDto.success();
    }

    @DeleteMapping
    public ResponseDto<?> delete(@RequestParam String uid) {
        userService.deleteUser(uid);
        return ResponseDto.success();
    }

    @GetMapping
    public ResponseDto<UserRespDto> getUser(@RequestParam @NotBlank String uid) {
        UserVo user = userService.getUserByUid(uid);
        return ResponseDto.success(
                UserRespDto.builder()
                        .uid(user.getUid())
                        .userName(user.getUserName())
                        .userStatus(user.getUserStatus())
                        .createTime(user.getCreateTime())
                        .build()
        );
    }

    @GetMapping("/page")
    public ResponseDto<Page<User>> getUserPaging(@RequestParam Pagination page) {
        Pageable pageable = PageRequest.of(page.getPage(), page.getSize(), Sort.by("id").descending());
        Page<User> userPage = userService.findUserPaging(pageable);
        return ResponseDto.success(userPage);
    }

}
