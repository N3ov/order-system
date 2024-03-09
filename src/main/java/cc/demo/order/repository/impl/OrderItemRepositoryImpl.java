package cc.demo.order.repository.impl;

import cc.demo.order.model.OrderItem;
import cc.demo.order.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepository {

    private final NamedParameterJdbcTemplate template;
    private final Integer BATCH_SIZE = 5;

    public int save(List<OrderItem> orderItems) {
        int count = 0;
        Date now = new Date();
        String sql = """
                INSERT INTO order_item (order_uid, product_id, quantity, create_time)
                VALUES (:orderUid, :productId, :quantity, :createTime)
                """;
        List<SqlParameterSource> args = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            SqlParameterSource source = new MapSqlParameterSource()
                    .addValue("orderUid", orderItem.getOrderUid())
                    .addValue("productId", orderItem.getProductId())
                    .addValue("quantity", orderItem.getQuantity())
                    .addValue("createTime", now);

            args.add(source);
            if (args.size() >= BATCH_SIZE) {
                int[] updateSize = template.batchUpdate(sql, args.toArray(new SqlParameterSource[0]));
                count += Arrays.stream(updateSize).sum();
                args.clear();
            }
        }

        int[] updateSize = template.batchUpdate(sql, args.toArray(new SqlParameterSource[0]));
        count += Arrays.stream(updateSize).sum();

        return count;
    }
}
