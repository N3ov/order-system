package cc.demo.order.infra.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");

    final String role;

    RoleEnum(String role) {
        this.role = role;
    }
}
