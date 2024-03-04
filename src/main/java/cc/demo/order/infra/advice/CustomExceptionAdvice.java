package cc.demo.order.infra.advice;

import cc.demo.order.infra.exception.*;
import cc.demo.order.infra.response.ResponseFailDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionAdvice {

    @ExceptionHandler({LoginException.class, ProductException.class, UserException.class, OrderException.class})
    public ResponseEntity<ResponseFailDto> handleCustomException(AbstractCustomerException e) {
        return ResponseFailDto.builder()
                .errorCode(e.getCode())
                .message(e.getMessage())
                .build()
                .toResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
