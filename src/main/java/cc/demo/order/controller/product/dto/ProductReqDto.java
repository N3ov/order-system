package cc.demo.order.controller.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductReqDto {
    String productName;
    String productPrice;
    String productDesc;
    Integer count;
}
