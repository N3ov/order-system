package cc.demo.order.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class OrderInfoVo {

    private String orderUid;

    private BigDecimal totalPrice;

    private int orderStatus;

    private Date createTime;

    private Date updateTime;

    private Date endTime;

    private Date paymentTime;

}
