package cc.demo.order.controller.order.dto;

import cc.demo.order.dto.Pagination;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

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
