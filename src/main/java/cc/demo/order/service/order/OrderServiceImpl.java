package cc.demo.order.service.order;

import cc.demo.order.controller.order.dto.OrderPagingReq;
import cc.demo.order.controller.order.dto.OrderReqDto;
import cc.demo.order.dto.OrderItemDto;
import cc.demo.order.infra.enums.OrderStatusEnum;
import cc.demo.order.infra.enums.ProductStatusEnum;
import cc.demo.order.infra.util.UidUtil;
import cc.demo.order.model.OrderInfo;
import cc.demo.order.model.OrderItem;
import cc.demo.order.model.User;
import cc.demo.order.repository.OrderInfoRepository;
import cc.demo.order.repository.OrderItemRepository;
import cc.demo.order.service.product.ProductService;
import cc.demo.order.service.user.UserService;
import cc.demo.order.vo.OrderCalculateVo;
import cc.demo.order.vo.OrderPagingVo;
import cc.demo.order.vo.ProductVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final ProductService productService;

    private final OrderInfoRepository orderInfoRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    @Override
    public void createOrder(OrderReqDto dto) {
        String uid = UidUtil.generateUid(15);
        Date now = new Date();
        List<OrderItemDto> orderItemDtoList = convertItems(dto.getProducts());

        List<Long> productIds = orderItemDtoList.stream().map(OrderItemDto::getProductId).collect(Collectors.toList());
        // TODO check product exist or available
        checkOderItems();

        Map<Long, ProductVo> productVoMap = productService.getProducts(productIds).stream().collect(Collectors.toMap(ProductVo::getProductId, p -> p));

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemDto orderItemDto : orderItemDtoList) {
            ProductVo productVo = productVoMap.get(orderItemDto.getProductId());

            if (productVo != null && ProductStatusEnum.AVAILABLE.getCode().equals(productVo.getProductStatus())) {
                OrderItem orderItem = OrderItem.builder()
                        .orderUid(uid)
                        .productId(productVo.getProductId())
                        .quantity(orderItemDto.getQuantity())
                        .createTime(now)
                        .build();

                orderItems.add(orderItem);
            }
        }

        Optional<User> user = Optional.ofNullable(userService.getUser(dto.getUserUid()));
        if (!orderItemDtoList.isEmpty() && user.isPresent()) {
            orderInfoRepository.createOrder(
                    OrderInfo.builder()
                            .orderUid(uid)
                            .userId(user.get().getId())
                            .orderStatus(OrderStatusEnum.UNPAID.getCode())
                            .createTime(now)
                            .build());

            orderItemRepository.save(orderItems);
        }

    }

    @Override
    public Page<List<OrderPagingVo>> getOrderPaging(OrderPagingReq dto) {

        Optional<User> user = Optional.ofNullable(userService.getUser(dto.getUserUid()));
        if (user.isPresent()) {
            List<OrderPagingVo> pageResult = orderInfoRepository.getOrderPaging(dto, user.get().getId());
            return new PageImpl<>(Collections.singletonList(pageResult));
        }
        return new PageImpl<>(Collections.emptyList());
    }

    @Override
    public List<OrderCalculateVo> getOrderCalculate(int count) {
        return orderInfoRepository.getOrderCalculate(count);
    }

    private List<OrderItemDto> convertItems(String items) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(items, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return List.of();
    }

    private void checkOderItems() {
    }
}
