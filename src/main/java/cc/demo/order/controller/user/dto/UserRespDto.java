package cc.demo.order.controller.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserRespDto {
    private String uid;
    private String userName;
    private String userStatus;
    private Date createTime;
}
