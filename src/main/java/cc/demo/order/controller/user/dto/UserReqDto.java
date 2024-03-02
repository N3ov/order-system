package cc.demo.order.controller.user.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserReqDto {

    @NotBlank(message = "user name can't be null or blank")
    private String userName;

    @NotBlank(message = "password can't be null or blank")
    private String password;

    @Email(message = "mail format not match")
    private String email;

}
