package cc.demo.order.repository.impl;

import cc.demo.order.model.Product;
import cc.demo.order.repository.ProductRepository;
import cc.demo.order.vo.ProductVo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final NamedParameterJdbcTemplate template;

    @Override
    public ProductVo findById(long productId) {
        String sql = "SELECT * FROM product WHERE product_id = :productId";
        SqlParameterSource map = new MapSqlParameterSource().addValue("productId", productId);
        return template.queryForObject(sql, map, new BeanPropertyRowMapper<>(ProductVo.class));
    }

    @Override
    public List<ProductVo> findProducts(List<Long> productIds) {
        String sql = "SELECT * FROM product WHERE product_id IN (:productIds)";
        SqlParameterSource map = new MapSqlParameterSource().addValue("productIds", productIds);
        return template.query(sql, map, new BeanPropertyRowMapper<>(ProductVo.class));
    }

    @Override
    public List<ProductVo> findAllProducts() {
        String sql = "SELECT * FROM product";
        return template.query(sql, new BeanPropertyRowMapper<>(ProductVo.class));
    }

    @Override
    public int create(Product product) {
        String sql = """
                INSERT INTO product (product_id, product_name, price, count, product_status, product_desc, create_time)
                VALUES (:productId, :productName, :price, :count, :productStatus, :productDesc, :createTime)
                """;

        return template.update(sql, new BeanPropertySqlParameterSource(product));
    }

    @Override
    public int updateCountAndVersion(int count, long productId, int version) {
        String sql = """
                UPDATE product SET count = (count - :count), sale = (sale + :count), version = :version + 1
                WHERE product_id = :productId AND version = :version
                """;
        SqlParameterSource map = new MapSqlParameterSource()
                .addValue("count", count)
                .addValue("productId", productId)
                .addValue("version", version);

        return template.update(sql, map);
    }


}
