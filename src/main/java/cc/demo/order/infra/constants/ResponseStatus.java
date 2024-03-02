package cc.demo.order.infra.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum ResponseStatus {

    SUCCESS(200, "Success"),
    FAIL(400, "Fail"),

    HTTP_STATUS_200(200, "OK"),
    HTTP_STATUS_400(400, "Bad Request"),
    HTTP_STATUS_401(401, "Unauthorized"),
    HTTP_STATUS_403(403, "Forbidden"),
    HTTP_STATUS_404(404, "Not Found"),
    HTTP_STATUS_500(500, "Internal Server Error");

    private final int responseCode;
    private final String description;

    public static final List<ResponseStatus> HTTP_STATUS_LIST = List.of(HTTP_STATUS_200, HTTP_STATUS_400, HTTP_STATUS_401, HTTP_STATUS_403, HTTP_STATUS_404, HTTP_STATUS_500);


}
