package cc.demo.order.infra.exception;

public class UserException extends AbstractCustomerException {

    public UserException(Integer code, String message) {
        super(code, message);
    }

    public UserException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
