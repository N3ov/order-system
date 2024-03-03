package cc.demo.order.repository;

import cc.demo.order.controller.order.dto.OrderPagingReq;
import cc.demo.order.model.OrderInfo;
import cc.demo.order.vo.OrderCalculateVo;
import cc.demo.order.vo.OrderPagingVo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderInfoRepository {

    private final NamedParameterJdbcTemplate template;

    public int createOrder(OrderInfo order) {
        String sql = """
                INSERT INTO order_info (order_uid, user_id, total_price, order_status, create_time)
                VALUES (:orderUid, :userId, :orderStatus, :totalPrice, :createTime)
                """;
        return template.update(sql, new BeanPropertySqlParameterSource(order));
    }

    public List<OrderPagingVo> getOrderPaging(OrderPagingReq dto, long userId) {
        String sql = """
                select oi.*, oit.quantity, p.product_name from order_info oi
                join order_item oit on oit.order_uid = oi.order_uid
                join product p on p.product_id = oit.product_id
                where oi.user_id = :userId
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);

        StringBuilder sb = new StringBuilder(sql);

        Optional.ofNullable(dto.getOrderUid())
                .filter(uid -> !uid.isEmpty())
                .ifPresent(uid -> {
                    checkSql(sb);
                    sb.append(" oi.order_uid = :order_uid");
                    params.addValue("order_uid", uid);
                });

        Optional.ofNullable(dto.getProductName())
                .filter(name -> !name.isEmpty())
                .ifPresent(name -> {
                    checkSql(sb);
                    sb.append(" p.product_name = :productName");
                    params.addValue("productName", name);
                });

        Optional.ofNullable(dto.getBuyTime())
                .ifPresent(t -> {
                    checkSql(sb);
                    sb.append(" oi.create_time = :buyTime");
                    params.addValue("buyTime", t);
                });

        sb.append(" LIMIT :limit OFFSET :offset");
        params.addValue("offset", dto.getPage());
        params.addValue("limit", dto.getSize());

        return template.query(sb.toString(), params, new BeanPropertyRowMapper<>(OrderPagingVo.class));
    }

    public List<OrderCalculateVo> getOrderCalculate(int count) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        String sql = """
            select user_id, count(order_uid) as order_count from order_info
            group by user_id
            having count(order_uid) > :count
        """;

        params.addValue("count", count);

        return template.query(sql, params, new BeanPropertyRowMapper<>(OrderCalculateVo.class));
    }

    private static void checkSql(StringBuilder sb) {
        if(sb.toString().compareToIgnoreCase("where") <= 0) {
            sb.append(" where");
        } else {
            sb.append(" and");
        }
    }

}
