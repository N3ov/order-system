package cc.demo.order.infra.exception;

import lombok.Getter;

public class OrderException extends RuntimeException {

    @Getter
    private final Integer code;

    public OrderException(int code, String message) {
        super(message);
        this.code = code;
    }

    public OrderException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
