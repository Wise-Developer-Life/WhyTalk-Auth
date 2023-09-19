package com.wisedevlife.whytalkauth.service.implement;

import com.wisedevlife.whytalkauth.common.constants.AuthConstant;
import com.wisedevlife.whytalkauth.common.constants.RedisConstant;
import com.wisedevlife.whytalkauth.common.enums.ErrorCodeEnum;
import com.wisedevlife.whytalkauth.common.exception.AuthException;
import com.wisedevlife.whytalkauth.common.utils.JsonUtil;
import com.wisedevlife.whytalkauth.common.utils.RedisUtil;
import com.wisedevlife.whytalkauth.entity.User;
import com.wisedevlife.whytalkauth.model.AccessTokenInfo;
import com.wisedevlife.whytalkauth.model.AuthInfo;
import com.wisedevlife.whytalkauth.repository.UserRepository;
import com.wisedevlife.whytalkauth.service.AuthService;
import com.wisedevlife.whytalkauth.service.JwtService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@Primary
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final RedisUtil redisUtil;
    private final UserRepository userRepository;

    /**
     * TODO: validate user information, including username and password
     *
     * @param email email from SignInRequest
     * @param password password from SignInRequest
     * @return AuthInfo
     */
    @Override
    public AuthInfo signIn(String email, String password) {
        return generateAuthInfoByEmail(email);
    }

    @Override
    public void logout(String accessToken) {
        jwtService.validateToken(accessToken);
        AccessTokenInfo accessTokenInfo =
                jwtService.getClaimBody(accessToken, AccessTokenInfo.class);
        redisUtil.deleteKey(RedisConstant.REFRESH_TOKEN + accessTokenInfo.getRefreshToken());
    }

    @Override
    public AuthInfo refreshAccessToken(String refreshToken) {
        jwtService.validateToken(refreshToken);
        String username = jwtService.getClaimSubject(refreshToken);
        AuthInfo authInfo = generateAuthInfoByEmail(username);
        redisUtil.deleteKey(RedisConstant.REFRESH_TOKEN + refreshToken);
        return authInfo;
    }

    @Override
    public String removeBearerPrefix(String header) {
        return StringUtils.removeStart(header, AuthConstant.JWT_HEADER_PREFIX);
    }

    private AuthInfo generateAuthInfoByEmail(String email) {
        String refreshToken = generateRefreshToken(email);
        String accessToken = generateAccessToken(email, refreshToken);
        String redisKey = RedisConstant.REFRESH_TOKEN + refreshToken;
        redisUtil.setWithExpired(redisKey, email, AuthConstant.ACCESS_EXPIRATION_DURATION);

        return AuthInfo.of(accessToken, refreshToken, AuthConstant.ACCESS_EXPIRATION_DURATION);
    }

    private String generateRefreshToken(String email) {
        return jwtService.generateToken(email, AuthConstant.REFRESH_EXPIRATION_DURATION);
    }

    private String generateAccessToken(String email, String refreshToken) {
        AccessTokenInfo accessTokenInfo = AccessTokenInfo.of(refreshToken);
        return jwtService.generateToken(
                JsonUtil.toMap(accessTokenInfo), email, AuthConstant.ACCESS_EXPIRATION_DURATION);
    }

    @Override
    public AuthInfo verifyOauthUser(OAuth2AuthenticationToken token) {
        if (token == null || !token.isAuthenticated()) {
            throw new AuthException(
                    ErrorCodeEnum.OAUTH_AUTHENTICATION_FAILED, HttpStatus.UNAUTHORIZED);
        }
        String email = token.getPrincipal().getAttribute("email");
        log.info("user email: {}", email);

        User user = userRepository.findByEmail(email);
        if (user == null) {
            user = User.ofCreate(email);
        } else {
            user.setLastLoginTime(LocalDateTime.now());
        }

        userRepository.save(user);

        return generateAuthInfoByEmail(email);
    }
}
