package cc.demo.order.infra.enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnum {
    AVAILABLE(0),
    UN_AVAILABLE(1);

    final Integer code;
    ProductStatusEnum(int code) {
        this.code = code;
    }
}
