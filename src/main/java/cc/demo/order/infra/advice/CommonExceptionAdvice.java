package cc.demo.order.infra.advice;

import cc.demo.order.infra.response.ResponseErrorDto;
import cc.demo.order.infra.response.ResponseFailDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.Set;


@ControllerAdvice
public class CommonExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ResponseErrorDto> handleMethArgNotValid(MethodArgumentNotValidException e) {
        ResponseErrorDto dto = new ResponseErrorDto();

        if (e.getBindingResult().getErrorCount() > 0) {
            for (ObjectError error : e.getBindingResult().getAllErrors()) {
                if (error instanceof FieldError) {
                    dto.addError(((FieldError) error).getField(), error.getDefaultMessage());
                } else {
                    dto.addError(error.getObjectName(), error.getDefaultMessage());
                }
            }
        } else {
            dto.setMessage(e.getMessage());
        }
        return dto.toResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            MissingServletRequestPartException.class,
            MissingServletRequestParameterException.class,
            ServletRequestBindingException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<ResponseFailDto> handleBadRequest(Exception e) {
        HttpStatus statue = HttpStatus.BAD_REQUEST;

        if (e instanceof MissingServletRequestParameterException) {
            String exceptionName = ((MissingServletRequestParameterException) e).getParameterName();
            return toResponseFailDTOResponseEntity(statue, exceptionName);
        } else if (e instanceof MissingServletRequestPartException) {
            String exceptionName = ((MissingServletRequestPartException) e).getRequestPartName();
            return toResponseFailDTOResponseEntity(statue, exceptionName);
        } else {
            return toResponseFailDTOResponseEntity(statue, e.getMessage());
        }
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseErrorDto> handleConstraintViolation(ConstraintViolationException e) {
        ResponseErrorDto dto = new ResponseErrorDto();
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        if (null != constraintViolations && constraintViolations.size() > 0) {
            for (ConstraintViolation<?> constraintViolation : constraintViolations) {
                dto.addError(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            }
        } else {
            dto.setMessage(e.getMessage());
        }
        return dto.toResponseEntity(HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ResponseFailDto> toResponseFailDTOResponseEntity(HttpStatus status, String exceptionName) {
        ResponseFailDto dto = new ResponseFailDto();
        dto.setMessage(exceptionName);

        return new ResponseEntity<>(dto, status);
    }
}
