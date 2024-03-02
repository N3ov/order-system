package cc.demo.order.service.order;

import cc.demo.order.controller.order.dto.OrderPagingReq;
import cc.demo.order.controller.order.dto.OrderReqDto;
import cc.demo.order.vo.OrderCalculateVo;
import cc.demo.order.vo.OrderInfoVo;
import cc.demo.order.vo.OrderPagingVo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    OrderInfoVo createOrder(OrderReqDto dto);

    Page<List<OrderPagingVo>> getOrderPaging(OrderPagingReq dto);

    List<OrderCalculateVo> getOrderCalculate(int count);
}
