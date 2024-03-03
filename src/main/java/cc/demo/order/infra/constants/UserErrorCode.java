package cc.demo.order.infra.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorCode {

    USER_NOT_FOUND(0, "USER NOT FOUND"),
    USER_NOT_ACTIVE(1, "USER NOT ACTIVE"),
    USER_NOT_LOGIN(2, "USER NOT LOGIN"),
    USER_CREATE_FAILED(3, "USER CREATE FAILED"),
    USER_CREATE_ROLE_FAILED(4, "USER CREATE ROLE FAILED"),
    USER_UPDATE_FAILED(5, "USER UPDATE FAILED"),
    USER_DELETE_ROLE_FAILED(6, "USER DELETE ROLE FAILED"),
    USER_DELETE_FAILED(7, "USER DELETE FAILED");

    private final Integer code;
    private final String message;

}
