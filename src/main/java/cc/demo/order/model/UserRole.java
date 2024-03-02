package cc.demo.order.model;

import cc.demo.order.infra.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {
    private long userId;
    private RoleEnum role;
}
