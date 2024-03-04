package cc.demo.order.infra.exception;

public class ProductException extends AbstractCustomerException {

    public ProductException(Integer code, String message) {
        super(code, message);
    }

    public ProductException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
