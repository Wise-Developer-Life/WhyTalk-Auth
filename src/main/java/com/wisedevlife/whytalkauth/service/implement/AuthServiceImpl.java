package com.wisedevlife.whytalkauth.service.implement;

import com.wisedevlife.whytalkauth.common.constants.AuthConstant;
import com.wisedevlife.whytalkauth.common.constants.RedisConstant;
import com.wisedevlife.whytalkauth.common.utils.JsonUtil;
import com.wisedevlife.whytalkauth.common.utils.RedisUtil;
import com.wisedevlife.whytalkauth.model.AccessTokenInfo;
import com.wisedevlife.whytalkauth.model.AuthInfo;
import com.wisedevlife.whytalkauth.service.AuthService;
import com.wisedevlife.whytalkauth.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final RedisUtil redisUtil;

    /**
     * TODO: validate user information, including username and password
     *
     * @param username username from SignInRequest
     * @param password password from SignInRequest
     * @return AuthInfo
     */
    @Override
    public AuthInfo signIn(String username, String password) {
        return generateAuthInfoByUsername(username);
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
        AuthInfo authInfo = generateAuthInfoByUsername(username);
        redisUtil.deleteKey(RedisConstant.REFRESH_TOKEN + refreshToken);
        return authInfo;
    }

    @Override
    public String removeBearerPrefix(String header) {
        return StringUtils.removeStart(header, AuthConstant.JWT_HEADER_PREFIX);
    }

    private AuthInfo generateAuthInfoByUsername(String username) {
        String refreshToken = generateRefreshToken(username);
        String accessToken = generateAccessToken(username, refreshToken);
        String redisKey = RedisConstant.REFRESH_TOKEN + refreshToken;
        redisUtil.setWithExpired(redisKey, username, AuthConstant.ACCESS_EXPIRATION_DURATION);

        return AuthInfo.of(accessToken, refreshToken, AuthConstant.ACCESS_EXPIRATION_DURATION);
    }

    private String generateRefreshToken(String username) {
        return jwtService.generateToken(username, AuthConstant.REFRESH_EXPIRATION_DURATION);
    }

    private String generateAccessToken(String username, String refreshToken) {
        AccessTokenInfo accessTokenInfo = AccessTokenInfo.of(refreshToken);
        return jwtService.generateToken(
                JsonUtil.toMap(accessTokenInfo), username, AuthConstant.ACCESS_EXPIRATION_DURATION);
    }
}
