package cc.demo.order.controller.order.dto;

import cc.demo.order.dto.Pagination;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderPagingReq extends Pagination {
    @NotBlank
    String userUid;

    String orderUid;

    String productName;

    Long buyTime;
}
