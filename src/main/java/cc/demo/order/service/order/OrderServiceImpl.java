package cc.demo.order.service.order;

import cc.demo.order.controller.order.dto.OrderPagingReq;
import cc.demo.order.controller.order.dto.OrderReqDto;
import cc.demo.order.dto.OrderItemDto;
import cc.demo.order.infra.enums.OrderStatusEnum;
import cc.demo.order.infra.enums.ProductStatusEnum;
import cc.demo.order.infra.exception.OrderException;
import cc.demo.order.infra.exception.UserException;
import cc.demo.order.infra.util.UidUtil;
import cc.demo.order.model.OrderInfo;
import cc.demo.order.model.OrderItem;
import cc.demo.order.repository.OrderInfoRepository;
import cc.demo.order.repository.OrderItemRepository;
import cc.demo.order.service.product.ProductService;
import cc.demo.order.service.user.UserService;
import cc.demo.order.vo.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final UserService userService;
    private final ProductService productService;

    private final OrderInfoRepository orderInfoRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    @Override
    public OrderInfoVo createOrder(OrderReqDto dto) {

        String uid = UidUtil.generateUid(15);
        Date now = new Date();
        int orderStatus = OrderStatusEnum.UNPAID.getCode();

        // check user
        Optional<UserVo> user = Optional.ofNullable(userService.getUserByUid(dto.getUserUid()));
        if (user.isEmpty()) {
            LOGGER.info("Uid: [{}], User not exit", dto.getUserUid());
            throw new UserException(0, "User not exit");
        }
        LOGGER.info("Uid:[{}] user checked", dto.getUserUid());

        Map<Long, OrderItemDto> orderItemDtoMap = dto.getProducts().stream().collect(Collectors.toMap(OrderItemDto::getProductId, p -> p));
        // check product
        List<Long> productIds = dto.getProducts().stream().map(OrderItemDto::getProductId).collect(Collectors.toList());
        List<ProductVo> productVos = productService.getProducts(productIds);
        Map<Long, ProductVo> productVoMap = productVos.stream()
                .filter(p -> ProductStatusEnum.AVAILABLE.getCode().equals(p.getProductStatus()))
                .filter(p -> p.getCount() > orderItemDtoMap.get(p.getProductId()).getQuantity())
                .collect(Collectors.toMap(ProductVo::getProductId, p -> p));
        if (productVoMap.isEmpty()) {
            LOGGER.info("products: [{}], Products not exit", dto.getProducts());
            throw new UserException(1, "Product not exit");
        }
        LOGGER.info("Products: [{}] checked", productIds);

        // TODO optimize this
        //product reduce count
        productVos.forEach(p -> productService.reduceCount(orderItemDtoMap.get(p.getProductId()).getQuantity(), p.getProductId(), p.getVersion()));

        List<OrderItem> orderItems = dto.getProducts().stream()
                .filter(orderItemDto ->
                        productVoMap.containsKey(orderItemDto.getProductId())
                                && productVoMap.get(orderItemDto.getProductId()).getProductStatus() == ProductStatusEnum.AVAILABLE.getCode()
                                && productVoMap.get(orderItemDto.getProductId()).getCount() > orderItemDto.getQuantity()
                ).map(orderItemDto -> {
                    ProductVo productVo = productVoMap.get(orderItemDto.getProductId());
                    return OrderItem.builder()
                            .orderUid(uid)
                            .productId(productVo.getProductId())
                            .quantity(orderItemDto.getQuantity())
                            .createTime(now)
                            .build();
                }).toList();

        // calculate price
        BigDecimal totalPrice = getTotalPrice(dto, productVoMap, orderItems);

        if (!orderItems.isEmpty()) {
            createOrder(uid, now, orderStatus, user.get(), orderItems, totalPrice);
        }

        return OrderInfoVo.builder()
                .orderUid(uid)
                .totalPrice(totalPrice)
                .createTime(now)
                .orderStatus(orderStatus)
                .build();

    }

    private static BigDecimal getTotalPrice(OrderReqDto dto, Map<Long, ProductVo> productVoMap, List<OrderItem> orderItems) {
        BigDecimal totalPrice = orderItems.stream()
                .map(orderItem -> {
                    BigDecimal quantity = BigDecimal.valueOf(orderItem.getQuantity());
                    BigDecimal pricePerUnit = productVoMap.get(orderItem.getProductId()).getPrice();
                    return quantity.multiply(pricePerUnit).doubleValue();
                })
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        LOGGER.info("Uid:[{}] User totalPrice: [{}]", dto.getUserUid(), totalPrice);
        return totalPrice;
    }

    private void createOrder(String uid, Date now, int orderStatus, UserVo user, List<OrderItem> orderItems, BigDecimal totalPrice) {
        int oderInfoCreatedCount = orderInfoRepository.createOrder(
                OrderInfo.builder()
                        .orderUid(uid)
                        .userId(user.getId())
                        .orderStatus(orderStatus)
                        .totalPrice(totalPrice)
                        .createTime(now)
                        .build());

        int orderItemCount = orderItemRepository.save(orderItems);
        if (oderInfoCreatedCount == 0 || orderItemCount < orderItems.size()) {
            throw new OrderException(0, "Create order failed");
        }
    }

    @Override
    public Page<List<OrderPagingVo>> getOrderPaging(OrderPagingReq dto) {

        Optional<UserVo> user = Optional.ofNullable(userService.getUserByUid(dto.getUserUid()));
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

    private void checkOderItems() {
    }
}
