package cc.demo.order.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPagingVo {
    private long orderId;

    private String orderUid;

    private long userId;

    private BigDecimal totalPrice;

    private int orderStatus;

    private Date createTime;

    private String productName;

    private int quantity;

}
