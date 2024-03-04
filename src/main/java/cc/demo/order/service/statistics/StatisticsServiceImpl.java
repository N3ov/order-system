package cc.demo.order.service.statistics;

import cc.demo.order.repository.OrderInfoRepository;
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

    private final UserService userService;
    private final OrderInfoRepository orderInfoRepository;


    public StatisticsVo statistics(int count) {
        List<OrderCalculateVo> orderCounts = getOrderCalculate(count);
        Map<Long, UserInfoVo> userMap = new HashMap<>();
        if (!orderCounts.isEmpty()) {
            List<Long> userIds = orderCounts.stream().map(OrderCalculateVo::getUserId).toList();
            List<UserInfoVo> userInfoVos = userService.getUsers(userIds);
            userMap = userInfoVos.stream()
                    .collect(Collectors.toMap(UserInfoVo::getId, user -> user));
        }

        return StatisticsVo.builder()
                .users(userMap)
                .data(orderCounts)
                .build();
    }

    public List<OrderCalculateVo> getOrderCalculate(int count) {
        return orderInfoRepository.getOrderCalculate(count);
    }
}
