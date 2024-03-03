package cc.demo.order.controller.security.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginReqDto {
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
}
