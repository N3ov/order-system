package cc.demo.order.repository.impl;

import cc.demo.order.model.OrderInfo;
import cc.demo.order.repository.OrderInfoRepository;
import cc.demo.order.vo.OrderCalculateVo;
import cc.demo.order.vo.OrderPagingVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderInfoRepositoryImpl implements OrderInfoRepository {

    private final NamedParameterJdbcTemplate template;

    @Override
    public int createOrder(OrderInfo order) {
        String sql = """
                INSERT INTO order_info (order_uid, user_id, total_price, order_status, create_time)
                VALUES (:orderUid, :userId, :totalPrice, :orderStatus, :createTime)
                """;
        return template.update(sql, new BeanPropertySqlParameterSource(order));
    }

    @Override
    public List<OrderPagingVo> getOrderPaging(Pageable page, long userId,String orderUid, String productName, String startTime, String endTime) {
        String sql = """
                select oi.*, oit.quantity, p.product_name from order_info oi
                join order_item oit on oit.order_uid = oi.order_uid
                join product p on p.product_id = oit.product_id
                where oi.user_id = :userId
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);

        StringBuilder sb = new StringBuilder(sql);

        Optional.ofNullable(orderUid)
                .filter(uid -> !uid.isBlank())
                .ifPresent(uid -> {
                    checkSql(sb);
                    sb.append(" oi.order_uid = :order_uid");
                    params.addValue("order_uid", uid);
                });

        Optional.ofNullable(productName)
                .filter(name -> !name.isBlank())
                .ifPresent(name -> {
                    checkSql(sb);
                    sb.append(" p.product_name = :productName");
                    params.addValue("productName", name);
                });

        if (!startTime.isBlank() && !endTime.isBlank()) {
            checkSql(sb);
            sb.append(" oi.create_time between :startTime and :endTime");
            params.addValue("startTime", startTime);
            params.addValue("endTime", endTime);
        }

        sb.append(" LIMIT :limit OFFSET :offset");
        params.addValue("offset", page.getOffset());
        params.addValue("limit", page.getPageSize());

        return template.query(sb.toString(), params, new BeanPropertyRowMapper<>(OrderPagingVo.class));
    }

    @Override
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
        if(!sb.toString().contains("where")) {
            sb.append(" where");
        } else {
            sb.append(" and");
        }
    }

}
