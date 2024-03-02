package cc.demo.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo {

    private long orderId;

    private String orderUid;

    private long userId;

    private int orderStatus;

    private Date createTime;

    private Date updateTime;

    private Date endTime;

    private Date paymentTime;
}
