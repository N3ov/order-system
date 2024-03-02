package cc.demo.order.service.statistics;

import cc.demo.order.service.order.OrderService;
import cc.demo.order.service.user.UserService;
import cc.demo.order.vo.OrderCalculateVo;
import cc.demo.order.vo.StatisticsVo;
import cc.demo.order.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final OrderService orderService;
    private final UserService userService;

    public StatisticsVo statistics(int count) {
        List<OrderCalculateVo> orderCounts = orderService.getOrderCalculate(count);

        List<UserVo> userVos = userService.getUsers(orderCounts.stream().map(OrderCalculateVo::getUserId).collect(Collectors.toList()));
        Map<String, UserVo> userMap = userVos.stream().collect(Collectors.toMap(UserVo::getUid, user -> user));

        return StatisticsVo.builder().user(userMap).build();
    }
}
