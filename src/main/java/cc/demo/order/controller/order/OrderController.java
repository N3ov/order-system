package cc.demo.order.controller.order;

import cc.demo.order.controller.order.dto.OrderPagingReq;
import cc.demo.order.controller.order.dto.OrderReqDto;
import cc.demo.order.infra.response.ResponseDto;
import cc.demo.order.service.order.OrderService;
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
    public ResponseDto<String> createOrder(@Valid @RequestBody OrderReqDto dto) {

        orderService.createOrder(dto);

        return ResponseDto.success();
    }

    @GetMapping("/page")
    public ResponseDto<Page<?>> getOrderPaging(OrderPagingReq dto) {

        Page<?> orderPaging = orderService.getOrderPaging(dto);
        return ResponseDto.success(orderPaging);
    }

}
