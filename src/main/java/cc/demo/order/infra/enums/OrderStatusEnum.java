package cc.demo.order.infra.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {

    UNPAID(0, "Unpaid"),
    PAID(10, "paid"),
    CANCELED(20, "canceled");


    final Integer code;
    final String message;

    OrderStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
