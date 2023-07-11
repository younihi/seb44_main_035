package com.server.server.domain.user.controller;

import com.server.server.domain.user.dto.UserDto;
import com.server.server.domain.user.entity.User;
import com.server.server.domain.user.mapper.UserMapper;
import com.server.server.domain.user.service.UserService;
import com.server.server.global.response.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class UserController {
    private final UserMapper mapper;
    private final UserService service;

    @PostMapping("/register")
    public ResponseEntity postUser(@RequestBody UserDto.Post requestBody) {
        User user = service.createUser(mapper.postToUser(requestBody));

        return new ResponseEntity(new SingleResponseDto<>(mapper.userToResponse(user)), HttpStatus.OK);
    }
}
