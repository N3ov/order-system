package cc.demo.order.controller.order.dto;

import cc.demo.order.dto.OrderItemDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderReqDto {

    @NotBlank
    private String userUid;

    @NotNull
    private List<OrderItemDto> products;
}
