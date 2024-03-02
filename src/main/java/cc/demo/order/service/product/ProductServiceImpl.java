package cc.demo.order.service.product;

import cc.demo.order.controller.product.dto.ProductReqDto;
import cc.demo.order.infra.enums.ProductStatusEnum;
import cc.demo.order.model.Product;
import cc.demo.order.repository.ProductRepository;
import cc.demo.order.vo.ProductVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public void create(ProductReqDto dto) {
        Product product = Product.builder()
                .productName(dto.getProductName())
                .productDesc(dto.getProductDesc())
                .price(new BigDecimal(dto.getProductPrice()))
                .createTime(new Date())
                .productStatus(ProductStatusEnum.AVAILABLE.getCode())
                .build();

        productRepository.create(product);
    }

    @Override
    public ProductVo getProduct(int productId) {
        return productRepository.findById(productId);
    }

    @Override
    public List<ProductVo> getProducts(List<Long> productIds) {
        return Collections.unmodifiableList(productRepository.findProducts(productIds));
    }
}
