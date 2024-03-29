package cc.demo.order.service.login;

import cc.demo.order.infra.exception.LoginException;
import cc.demo.order.infra.jwt.JwtTokenProvider;
import cc.demo.order.service.user.UserService;
import cc.demo.order.vo.UserRoleVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static cc.demo.order.infra.constants.LoginErrorCode.USER_NAME_OR_PASSWORD_ERROR;
import static cc.demo.order.infra.constants.LoginErrorCode.USER_TOKEN_VERIFY_FAILED;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final StringRedisTemplate redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Override
    public String login(String username, String password) {

        UserRoleVo userVo = validUser(username, password);
        String role = userVo.getRoleName();
        //TODO need auto remove expire token
        Optional<String> redisToken = Optional.ofNullable(redisTemplate.opsForValue().get(userVo.getUid()));

        if (redisToken.isPresent()) {
            Jws<Claims> claims = jwtTokenProvider.parserToken(redisToken.get());
            Claims body = claims.getBody();
            if (!body.getSubject().equals(userVo.getUserName()) || !body.get("auth").equals(role)) {
                throw new LoginException(USER_TOKEN_VERIFY_FAILED.getCode(), USER_TOKEN_VERIFY_FAILED.getMessage());
            }
        } else {
            String token = jwtTokenProvider.createToken(username, role);
            redisTemplate.opsForValue().set(userVo.getUid(), token);
            return token;
        }

        return redisToken.get();
    }


    private UserRoleVo validUser(String username, String password) {
        Optional<UserRoleVo> userRoleVo = Optional.ofNullable(userService.getUserByName(username));
        if (userRoleVo.isEmpty() || !userRoleVo.get().getUserName().equals(username) || !userRoleVo.get().getPasswd().equals(password)) {
            throw new LoginException(USER_NAME_OR_PASSWORD_ERROR.getCode(), USER_NAME_OR_PASSWORD_ERROR.getMessage());
        }

        return userRoleVo.get();
    }
}
