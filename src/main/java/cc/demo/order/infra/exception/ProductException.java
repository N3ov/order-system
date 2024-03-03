package cc.demo.order.infra.exception;

import lombok.Getter;

public class ProductException extends RuntimeException {

    @Getter
    private final Integer code;

    public ProductException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ProductException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}
