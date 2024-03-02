package cc.demo.order.infra.enums;

import lombok.Getter;

@Getter
public enum UserStatusEnum {
    ACTIVE(0, "active"),
    DEACTIVATE(1, "deactive");

    final int value;
    final String status;

    UserStatusEnum(int value, String status) {
        this.value = value;
        this.status = status;
    }
    public static String getStatus(int value) {
        for (UserStatusEnum statusEnum : UserStatusEnum.values()) {
            if (statusEnum.getValue() == value) {
                return statusEnum.getStatus();
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }
}
