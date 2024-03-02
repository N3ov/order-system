package cc.demo.order.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class OrderItemVo {

    private long orderId;

    private long productId;

    private int quantity;

    private BigDecimal totalPrice;

    private Date createTime;
}
