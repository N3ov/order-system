package cc.demo.order.controller.security;

import cc.demo.order.controller.security.dto.LoginReqDto;
import cc.demo.order.infra.response.ResponseDto;
import cc.demo.order.service.login.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseDto<String> login(@Valid @RequestBody LoginReqDto dto) {

        return ResponseDto.success(loginService.login(dto.getUserName(), dto.getPassword()));
    }
}
