package cc.demo.order.controller.statistics;

import cc.demo.order.infra.response.ResponseDto;
import cc.demo.order.service.statistics.StatisticsService;
import cc.demo.order.service.statistics.StatisticsServiceImpl;
import cc.demo.order.vo.StatisticsVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order/statistics")
@RequiredArgsConstructor
public class OrderStatisticsController {

    private final StatisticsService service;

    @GetMapping
    public ResponseDto<StatisticsVo> calculateUserOrder(@RequestParam("count") int count) {

        return ResponseDto.success(service.statistics(count));
    }
}
