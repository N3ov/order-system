package cc.demo.order.infra.response;

import cc.demo.order.infra.constants.ResponseStatus;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ResponseDto<T> implements Serializable {

    private int status;
    private long responseTime;
    private String message;
    private T data;

    public static <T> ResponseDto<T> success() {
        return success(null);
    }

    public static <T> ResponseDto<T> success(T data) {
        return ResponseDto.<T>builder()
                .status(ResponseStatus.SUCCESS.getResponseCode())
                .message(ResponseStatus.SUCCESS.getDescription())
                .responseTime(System.currentTimeMillis())
                .data(data)
                .build();
    }

    public static <T extends Serializable> ResponseDto<T> fail(String message) {
        return fail(null, message);
    }

    public static <T> ResponseDto<T> fail(T data, String message) {
        return ResponseDto.<T>builder()
                .status(ResponseStatus.FAIL.getResponseCode())
                .message(message)
                .responseTime(System.currentTimeMillis())
                .data(data)
                .build();
    }

}
