package cc.demo.order.controller.order;

import cc.demo.order.controller.order.dto.OrderPagingReq;
import cc.demo.order.controller.order.dto.OrderReqDto;
import cc.demo.order.infra.response.ResponseDto;
import cc.demo.order.service.order.OrderService;
import cc.demo.order.vo.OrderInfoVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseDto<OrderInfoVo> createOrder(@Valid @RequestBody OrderReqDto dto) {

        return ResponseDto.success(orderService.createOrder(dto));
    }

    @GetMapping("/page")
    public ResponseDto<Page<?>> getOrderPaging(OrderPagingReq dto) {

        Page<?> orderPaging = orderService.getOrderPaging(dto);
        return ResponseDto.success(orderPaging);
    }

}
