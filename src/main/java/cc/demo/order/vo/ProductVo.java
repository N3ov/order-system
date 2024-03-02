package cc.demo.order.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductVo {
    private int productId;

    private String productName;

    private String productDesc;

    private BigDecimal productPrice;

    private int productStatus;
}
