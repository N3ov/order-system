package cc.demo.order.service.product;

import cc.demo.order.controller.product.dto.ProductReqDto;
import cc.demo.order.vo.ProductVo;

import java.util.List;

public interface ProductService {
    void create(ProductReqDto dto);

    ProductVo getProduct(int productId);

    List<ProductVo> getProducts(List<Long> productIds);

    List<ProductVo> getAllProduct();
}
