package cc.demo.order.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRoleRepository {

    private final NamedParameterJdbcTemplate template;

    public int createUserRole(Long userId) {
        String sql = "INSERT INTO user_role (user_id, role_name) VALUES (:userId, 'ROLE_USER')";

        SqlParameterSource map = new MapSqlParameterSource().addValue("userId", userId);

        return template.update(sql, map);
    }

}
