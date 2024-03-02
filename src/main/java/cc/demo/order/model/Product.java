package cc.demo.order.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class Product {
    private long productId;

    private String productName;

    private BigDecimal price;

    private int productStatus;

    private String productDesc;

    private Date createTime;

    private Date updateTime;

}
