package cc.demo.order.repository;

import cc.demo.order.model.Product;
import cc.demo.order.vo.ProductVo;

import java.util.List;

public interface ProductRepository {
    ProductVo findById(long productId);

    List<ProductVo> findProducts(List<Long> productIds);

    List<ProductVo> findAllProducts();

    int create(Product product);

    int updateCountAndVersion(int count, long productId, int version);
}
