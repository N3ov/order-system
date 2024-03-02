package cc.demo.order.controller.user.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class UserRespDto implements Serializable {
    private String uid;
    private String userName;
    private int userStatus;
    private Date createTime;
}
