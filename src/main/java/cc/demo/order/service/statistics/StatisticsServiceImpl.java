package cc.demo.order.service.statistics;

import cc.demo.order.service.order.OrderService;
import cc.demo.order.service.user.UserService;
import cc.demo.order.vo.OrderCalculateVo;
import cc.demo.order.vo.StatisticsVo;
import cc.demo.order.vo.UserInfoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
        Map<String, UserInfoVo> userMap = new HashMap<>();
        if (!orderCounts.isEmpty()) {
            List<Long> userIds = orderCounts.stream().map(OrderCalculateVo::getUserId).toList();
            List<UserInfoVo> userInfoVos = userService.getUsers(userIds);
            userMap = userInfoVos.stream()
                    .collect(Collectors.toMap(UserInfoVo::getUid, user -> user));
        }
        
        return StatisticsVo.builder().users(userMap).build();
    }
}
