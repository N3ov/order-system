package cc.demo.order.repository.impl;

import cc.demo.order.model.User;
import cc.demo.order.repository.UserRepository;
import cc.demo.order.vo.UserInfoVo;
import cc.demo.order.vo.UserVo;
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
public class UserRepositoryImpl implements UserRepository {

    private final NamedParameterJdbcTemplate template;


    @Override
    public UserVo getUserByUid(String uid) {
        String sql = """
                select * from user where uid = :uid;
                """;

        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("uid", uid);
        return template.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(UserVo.class));
    }

    @Override
    public UserVo getUserByUsername(String userName) {
        String sql = """
                select * from user where user_name = :user_name;
                """;

        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("user_name", userName);

        return template.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(UserVo.class));
    }

    @Override
    public List<UserInfoVo> getUsersById(List<Long> userIds) {
        String sql = """
                select id, uid, user_name, email, create_time from user where id in (:userIds);
                """;

        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("userIds", userIds);

        return template.query(sql, namedParameters, new BeanPropertyRowMapper<>(UserInfoVo.class));
    }

    @Override
    public int createUser(User user) {
        String sql = """
                INSERT INTO user (uid, user_name, passwd, email, user_status, create_time)
                VALUES (:uid, :userName, :passwd, :email, :userStatus, :createTime)
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
    public int deleteUser(Long id) {
        String sql = """
                DELETE FROM user WHERE id = :id
                """;

        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);

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
                .addValue("offset", offset * limit)
                .addValue("limit", limit);

        List<User> users = template.query(sql, namedParameters, new BeanPropertyRowMapper<>(User.class));

        return new PageImpl<>(users, pageable, users.size());
    }
}
