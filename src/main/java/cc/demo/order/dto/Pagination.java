package cc.demo.order.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class Pagination {

    @Min(0)
    private Integer page;
    private Integer size;
}
