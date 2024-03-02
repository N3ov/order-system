package cc.demo.order.repository;

import cc.demo.order.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository{

    private final NamedParameterJdbcTemplate template;


    @Override
    public User getUserByUid(String uid) {
        String sql = """
                select * from user where uid = :uid;
                """;

        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("uid", uid);
        return template.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public int createUser(User user) {
        String sql = """
                INSERT INTO user (uid, user_name, passwd, email, user_status, create_time)
                VALUES (:uid, :userName, :password, :email, :userStatus, :createTime)
                """;

        return template.update(sql, new BeanPropertySqlParameterSource(user));
    }

    @Override
    public int updateUser(User user) {
        String sql = """
                UPDATE user SET user_name = :userName,
                passwd = :password,
                email = :email,
                WHERE uid = :uid
                """;

        return template.update(sql, new BeanPropertySqlParameterSource(user));
    }

    @Override
    public int deleteUser(String uid) {
        String sql = """
                DELETE FROM user WHERE uid = :uid
                """;

        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("uid", uid);

        return template.update(sql, namedParameters);
    }


    @Override
    public Page<User> findUserPaging(Pageable pageable) {
        long offset = pageable.getOffset();
        int limit = pageable.getPageSize();

        String sql = """
                SELECT * FROM user LIMIT :limit OFFSET :offset
                """;

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("offset", offset)
                .addValue("limit", limit);

        List<User> users = template.query(sql, namedParameters, new BeanPropertyRowMapper<>(User.class));

        return new PageImpl<>(users, pageable, users.size());
    }
}
