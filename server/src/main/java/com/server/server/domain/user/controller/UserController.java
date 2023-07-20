package com.server.server.domain.user.controller;

import com.server.server.domain.recipe.dto.RecipeDto;
import com.server.server.domain.recipe.entity.Recipe;
import com.server.server.domain.recipe.mapper.RecipeMapper;
import com.server.server.domain.user.dto.UserDto;
import com.server.server.domain.user.entity.User;
import com.server.server.domain.user.mapper.UserMapper;
import com.server.server.domain.user.service.UserService;
import com.server.server.global.response.MultiResponseDto;
import com.server.server.global.response.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;
    private final RecipeMapper recipeMapper;

    @PostMapping("/register")
    public ResponseEntity postUser(@RequestBody UserDto.Post requestBody) {
        User user = userService.createUser(userMapper.postToUser(requestBody));

        return new ResponseEntity(new SingleResponseDto<>(userMapper.userToResponse(user)), HttpStatus.OK);
    }

    @GetMapping("/{user-id}")
    public void getUser(@PathVariable("user-id") long userId) {
        User user = userService.findUser(userId);
        System.out.println(user.getRecipeList());
    }

    @GetMapping("/find/liked/{user-id}")
    public ResponseEntity getUserRecommend(@PathVariable("user-id") long userId,
                                           @RequestParam(value = "page", defaultValue = "1") int page,
                                           @RequestParam(value = "size", defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Recipe> recipePage = userService.findUserRecommendRecipe(userId, pageable);
        List<RecipeDto.ListResponse> responseList = recipeMapper.recipesToResponseList(recipePage.getContent());

        return new ResponseEntity(new MultiResponseDto<>(responseList, recipePage),HttpStatus.OK );
    }
    @GetMapping("/find/recipe/{user-id}")
    public ResponseEntity getUserRecipe(@PathVariable("user-id") long userId,
                                        @RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "size", defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Recipe> recipePage = userService.findUserRecipe(userId, pageable);
        List<RecipeDto.ListResponse> responseList = recipeMapper.recipesToResponseList(recipePage.getContent());

        return new ResponseEntity(new MultiResponseDto<>(responseList, recipePage),HttpStatus.OK );
    }
    @GetMapping("/find/comment/{user-id}")
    public ResponseEntity getUserComment(@PathVariable("user-id") long userId,
                                         @RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "size", defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Recipe> recipePage = userService.findUserCommentRecipe(userId, pageable);
        List<RecipeDto.ListResponse> responseList = recipeMapper.recipesToResponseList(recipePage.getContent());

        return new ResponseEntity(new MultiResponseDto<>(responseList, recipePage),HttpStatus.OK );
    }
}
