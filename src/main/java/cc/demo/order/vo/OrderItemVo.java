package cc.demo.order.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class OrderItemVo {

    private long orderId;

    private long productId;

    private int quantity;

    private Date createTime;
}
