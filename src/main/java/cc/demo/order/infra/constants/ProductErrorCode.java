package cc.demo.order.infra.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductErrorCode {
    PRODUCT_NOT_FOUND(0, "PRODUCT NOT FOUND"),
    PRODUCT_COUNT_NOT_ENOUGH(1, "PRODUCT COUNT NOT ENOUGH"),
    PRODUCT_REDUCED_FAILED(2, "PRODUCT REDUCED FAILED");
    private final Integer code;
    private final String message;

}
