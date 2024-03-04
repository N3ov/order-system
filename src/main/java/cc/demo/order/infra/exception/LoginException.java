package cc.demo.order.infra.exception;

public class LoginException extends AbstractCustomerException {

    public LoginException(Integer code, String message) {
        super(code, message);
    }

    public LoginException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
