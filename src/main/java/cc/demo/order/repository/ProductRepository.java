package cc.demo.order.repository;

import cc.demo.order.model.Product;
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
public class ProductRepository {

    private final NamedParameterJdbcTemplate template;

    public ProductVo findById(long productId) {
        String sql = "SELECT * FROM product WHERE product_id = :productId";
        SqlParameterSource map = new MapSqlParameterSource().addValue("productId", productId);
        return template.queryForObject(sql, map, new BeanPropertyRowMapper<>(ProductVo.class));
    }

    public List<ProductVo> findProducts(List<Long> productIds) {
        String sql = "SELECT * FROM product WHERE product_id IN (:productIds)";
        SqlParameterSource map = new MapSqlParameterSource().addValue("productIds", productIds);
        return template.query(sql, map, new BeanPropertyRowMapper<>(ProductVo.class));
    }

    public void create(Product product) {
        String sql = """
                INSERT INTO product (product_id, product_name, price, product_status, product_desc, create_time)
                VALUES (:productId, :productName, :price, :productStatus, :productDesc, :createTime)
                """;

        template.update(sql, new BeanPropertySqlParameterSource(product));
    }

}
