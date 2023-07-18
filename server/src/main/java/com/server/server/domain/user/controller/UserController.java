package com.server.server.domain.user.controller;

import com.server.server.domain.user.dto.UserDto;
import com.server.server.domain.user.entity.User;
import com.server.server.domain.user.mapper.UserMapper;
import com.server.server.domain.user.service.TemporaryTokenService;
import com.server.server.domain.user.service.UserService;
import com.server.server.global.response.SingleResponseDto;
import com.server.server.global.security.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.server.server.domain.user.dto.SignupForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class UserController {
    private final UserMapper mapper;
    private final UserService service;
    private final JwtTokenProvider jwtTokenProvider;

    private final TemporaryTokenService temporaryTokenService;

    @GetMapping("/temper")
    public ResponseEntity<String> getTemporaryToken() {
        String temporaryToken = temporaryTokenService.issueTemporaryToken();
        return ResponseEntity.ok(temporaryToken);
    }
/*
    //로그인 부분 그냥 놓음
    @PostMapping("/login")
    public ResponseEntity<String> loginSuccess(@RequestBody Map<String, String> loginForm) {
        String token = service.login(loginForm.get("username"), loginForm.get("password"));
        return ResponseEntity.ok(token);
    }

    @PostMapping("/signup")
    public Long signup(@RequestBody SignupForm signupForm) {
        return service.signup(signupForm);
    }

    @GetMapping("/signup/check/{email}/exists")
    public ResponseEntity<Boolean> checkEmailDuplicate(@PathVariable String email) {
        return ResponseEntity.ok(service.checkEmailExists(email));
    }
*/

}
