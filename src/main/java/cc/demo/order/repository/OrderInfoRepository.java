package cc.demo.order.repository;

import cc.demo.order.model.OrderInfo;
import cc.demo.order.vo.OrderCalculateVo;
import cc.demo.order.vo.OrderPagingVo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderInfoRepository {
    int createOrder(OrderInfo order);

    List<OrderPagingVo> getOrderPaging(Pageable page, long userId, String orderUid, String productName, String startTime, String endTime);

    List<OrderCalculateVo> getOrderCalculate(int count);
}
