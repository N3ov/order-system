package cc.demo.order.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class StatisticsVo<M extends Map<Long, ?>, T> {

    M users;
    T data;

}
