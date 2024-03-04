package cc.demo.order.infra.exception;

public class OrderException extends AbstractCustomerException {

    public OrderException(Integer code, String message) {
        super(code, message);
    }

    public OrderException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
