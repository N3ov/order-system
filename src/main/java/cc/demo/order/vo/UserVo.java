package cc.demo.order.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 123456789L; // 任何long值都可以

    private long id;
    private String uid;
    private String userName;
    private String password;
    private String email;
    private int status;
    private Date createTime;
}
