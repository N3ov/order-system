package cc.demo.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private long id;

    private String uid;

    private String userName;

    private String password;

    private String email;

    private int userStatus;

    private Date createTime;
}
