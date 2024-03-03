package cc.demo.order.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1256789L;

    private long roleId;
    private long userId;
    private String uid;
    private String roleName;
    private String userName;
    private String passwd;

}
