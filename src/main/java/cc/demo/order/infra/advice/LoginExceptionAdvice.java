package cc.demo.order.infra.advice;

import cc.demo.order.infra.exception.LoginException;
import cc.demo.order.infra.response.ResponseFailDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class LoginExceptionAdvice {

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ResponseFailDto> handleLoginException(LoginException e) {
        return ResponseFailDto.builder()
                .errorCode(e.getCode())
                .message(e.getMessage())
                .build()
                .toResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
