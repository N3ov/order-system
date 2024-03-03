package cc.demo.order.infra.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductErrorCode {
    PRODUCT_NOT_FOUND(0, "PRODUCT NOT FOUND");
    private final Integer code;
    private final String message;

}
