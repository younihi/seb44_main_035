package com.server.server.domain.user.controller;

import com.server.server.domain.comment.service.CommentService;
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
    private final CommentService commentService;

    @PostMapping("/register")
    public ResponseEntity postUser(@RequestBody UserDto.Post requestBody) {
        User user = service.createUser(mapper.postToUser(requestBody));

        return new ResponseEntity(new SingleResponseDto<>(mapper.userToResponse(user)), HttpStatus.OK);
    }

    //회원 정보 조회 - 현재 로그인한 member의 question 정보, answer 정보 가져오기
//    @GetMapping("/find/{user-id}")
//    public ResponseEntity getUserActivities(@PathVariable("user-id") @Positive long userId) {
//        //Recipe 정보
//        List<ResponseEntityRecipeDto.ResponseForUser> recipes= recipeService.getRecipeByUserId(userId);
//        //Answer 정보
//        List<CommentDto.CommentResponseDtoForUser> comments = commentService.getCommentByUserId(userId);
//
//        Map<String, Object> responseData = new HashMap<>();
//        responseData.put("recipes", recipes);
//        responseData.put("comments", comments);
//
//        return new ResponseEntity<>(
//                new MultiResponseDtoWithOutPage<>(responseData),
//                HttpStatus.OK
//        );
//    }

}
