package cc.demo.order.controller.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderReqDto {

    @NotBlank
    private String userUid;

    @NotBlank
    private String products;
}
