package cc.demo.order.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVo {
    private static final long serialVersionUID = 456789L; // 任何long值都可以
    private String uid;
    private String userName;
    private int userStatus;
    private Date createTime;
}
