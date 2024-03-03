package cc.demo.order.infra.exception;

import lombok.Getter;

public class UserException extends RuntimeException {

    @Getter
    private final Integer code;

    public UserException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public UserException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
