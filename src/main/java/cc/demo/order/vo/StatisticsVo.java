package cc.demo.order.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatisticsVo<T> {

    T users;

}
