package cc.demo.order.repository;

import cc.demo.order.model.OrderItem;

import java.util.List;

public interface OrderItemRepository {

    int save(List<OrderItem> orderItems);

}
