package cc.demo.order.infra.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderErrorCode {

    ORDER_CREATE_FAILED(0, "order created failed");

    private final Integer code;
    private final String message;
}
