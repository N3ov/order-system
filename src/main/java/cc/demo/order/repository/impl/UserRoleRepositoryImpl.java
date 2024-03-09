package cc.demo.order.repository.impl;

import cc.demo.order.repository.UserRoleRepository;
import cc.demo.order.vo.UserRoleVo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRoleRepositoryImpl implements UserRoleRepository {

    private final NamedParameterJdbcTemplate template;

    public int createUserRole(Long userId) {
        String sql = "INSERT INTO user_role (user_id, role_name) VALUES (:userId, 'ROLE_USER')";

        SqlParameterSource map = new MapSqlParameterSource().addValue("userId", userId);

        return template.update(sql, map);
    }

    public UserRoleVo getUserRoleByName(String userName) {
        String sql = """
                select ur.*, u.uid, u.user_name, u.passwd from user_role ur
                join user u on u.id = ur.user_id
                where u.user_name = :userName
                """;

        SqlParameterSource map = new MapSqlParameterSource().addValue("userName", userName);

        return template.queryForObject(sql, map, new BeanPropertyRowMapper<>(UserRoleVo.class));
    }

    public int deleteUserRole(Long userId) {
        String sql = "DELETE FROM user_role WHERE user_id = :userId";
        SqlParameterSource map = new MapSqlParameterSource().addValue("userId", userId);
        return template.update(sql, map);
    }

}
