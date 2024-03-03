package cc.demo.order.infra.exception;

import lombok.Getter;

public class LoginException extends RuntimeException {
    @Getter
    private final Integer code;

    public LoginException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public LoginException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}
