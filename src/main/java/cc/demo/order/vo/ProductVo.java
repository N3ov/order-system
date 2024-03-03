package cc.demo.order.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVo {
    private long productId;

    private String productName;

    private String productDesc;

    private BigDecimal price;

    private Integer count;

    private Integer sale;

    private Integer version;

    private int productStatus;
}
