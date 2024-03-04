package cc.demo.order.infra.exception;

import lombok.Getter;

public class AbstractCustomerException extends RuntimeException {
    @Getter
    private final Integer code;

    public AbstractCustomerException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public AbstractCustomerException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}
