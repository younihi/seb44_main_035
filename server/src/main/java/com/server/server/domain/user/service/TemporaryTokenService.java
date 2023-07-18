package com.server.server.domain.user.service;

import com.server.server.global.security.config.jwt.JwtTokenProvider;
import org.springframework.stereotype.Service;

@Service
public class TemporaryTokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public TemporaryTokenService(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    public String issueTemporaryToken() {
        String temporaryUserId = userService.generateTemporaryUserId();
        // 임시 토큰 생성
        String temporaryToken = jwtTokenProvider.generateTemporaryToken();

        // 생성된 임시 토큰 반환
        return temporaryToken;
    }
}