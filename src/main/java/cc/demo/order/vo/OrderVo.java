package cc.demo.order.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class OrderVo {

    private String orderUid;

    private BigDecimal price;

    private int orderStatus;

    private List<OrderItemVo> orderItemVoList;

    private Date createTime;

    private Date updateTime;

    private Date endTime;

    private Date paymentTime;

}
