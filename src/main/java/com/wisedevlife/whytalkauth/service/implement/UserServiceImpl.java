package com.wisedevlife.whytalkauth.service.implement;

import com.wisedevlife.whytalkauth.entity.User;
import com.wisedevlife.whytalkauth.repository.UserRepository;
import com.wisedevlife.whytalkauth.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final DefaultOAuth2UserService defaultOAuth2UserService;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = defaultOAuth2UserService.loadUser(userRequest);

        String email = user.getAttribute("email");
        log.info("user email: {}", email);

        if (!userRepository.existsByEmail(email)) {
            userRepository.save(User.builder().email(email).build());
        }
        return user;
    }
}
