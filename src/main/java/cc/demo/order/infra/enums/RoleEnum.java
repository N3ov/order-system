package cc.demo.order.infra.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {

    ROLE_ADMIN(0, "admin"),
    ROLE_USER(1, "user");

    final Integer code;
    final String role;

    RoleEnum(Integer code, String role) {
        this.code = code;
        this.role = role;
    }
}
