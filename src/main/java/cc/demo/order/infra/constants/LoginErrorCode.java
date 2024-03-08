package cc.demo.order.infra.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginErrorCode {
    USER_NOT_EXIST(0, "USER_NOT_EXIST"),
    USER_NAME_OR_PASSWORD_ERROR(1, "USER_NAME_OR_PASSWORD_ERROR"),
    USER_DISABLED(2,  "USER_DISABLED"),
    USER_TOKEN_VERIFY_FAILED(3, "USER_TOKEN_VERIFY_FAILED");

    private final Integer code;
    private final String message;

}
