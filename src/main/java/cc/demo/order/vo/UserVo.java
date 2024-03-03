package cc.demo.order.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVo implements Serializable {

    private static final long serialVersionUID = 1456789L;

    private long id;
    private String uid;
    private String userName;
    private String passwd;
    private String email;
    private int userStatus;
    private Date createTime;
}
