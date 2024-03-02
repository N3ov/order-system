package cc.demo.order.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class OrderItem {
    private long itemId;

    private String orderUid;

    private long productId;

    private int quantity;

    private Date createTime;

    private Date updateTime;
}
