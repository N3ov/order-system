package cc.demo.order.infra.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TimeErrorCode {
    START_DATE_AFTER_END_DATE(0, "start date after end date");

    private final Integer code;
    private final String message;
}
