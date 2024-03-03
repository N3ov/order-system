package cc.demo.order.service.product;

import cc.demo.order.controller.product.dto.ProductReqDto;
import cc.demo.order.infra.enums.ProductStatusEnum;
import cc.demo.order.infra.exception.ProductException;
import cc.demo.order.model.Product;
import cc.demo.order.repository.ProductRepository;
import cc.demo.order.vo.ProductVo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    @Override
    public void create(ProductReqDto dto) {
        Product product = Product.builder()
                .productName(dto.getProductName())
                .productDesc(dto.getProductDesc())
                .price(new BigDecimal(dto.getProductPrice()))
                .count(dto.getCount())
                .createTime(new Date())
                .productStatus(ProductStatusEnum.AVAILABLE.getCode())
                .build();

        int count = productRepository.create(product);
        if (count == 0) {
            LOGGER.info("Product created failed");
            throw new ProductException(0, "Product created failed");
        }
        LOGGER.info("Product created success");
    }

    @Cacheable(value = "productCache", key = "#productId", unless = "#result == null")
    @Override
    public ProductVo getProduct(int productId) {
        return productRepository.findById(productId);
    }

    @Override
    public List<ProductVo> getProducts(List<Long> productIds) {
        return Collections.unmodifiableList(productRepository.findProducts(productIds));
    }

    @Override
    public List<ProductVo> getAllProduct() {
        return Collections.unmodifiableList(productRepository.findAllProducts());
    }

    @Override
    public void reduceCount(int count, long productId, int version) {
        int updateCount = productRepository.updateCountAndVersion(count, productId, version);
        LOGGER.info("Reduce Count updated: {}", updateCount);
        if(updateCount == 0) {
            LOGGER.info("Reduce Count updated failed");
            throw new ProductException(1, "Reduce Count updated failed");
        }
    }

}
