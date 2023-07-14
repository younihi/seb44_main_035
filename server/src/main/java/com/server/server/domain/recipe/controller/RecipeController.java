package com.server.server.domain.recipe.controller;

import com.server.server.domain.recipe.dto.RecipeDto;
import com.server.server.domain.recipe.entity.Recipe;
import com.server.server.domain.recipe.mapper.RecipeMapper;
import com.server.server.domain.recipe.service.RecipeService;
import com.server.server.global.response.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipes")
@Validated
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeMapper recipeMapper;
    private final RecipeService recipeService;

    @PostMapping("/create")
    public ResponseEntity postRecipe(@RequestBody RecipeDto.Post requestBody) {    //레시피 등록
        Recipe recipe = recipeMapper.postToRecipe(requestBody);
        Recipe savedRecipe = recipeService.createRecipe(recipe);

        return new ResponseEntity<>(new SingleResponseDto(recipeMapper.recipeToPostResponse(savedRecipe)), HttpStatus.CREATED);
    }

    @PostMapping("/recommend/{recipe-id}/{user-id}")
    public ResponseEntity toggleRecipeRecommend(@PathVariable("recipe-id") long recipeId,
                                                @PathVariable("user-id") long userId) {    //레시피 추천(토글 형식)
        RecipeDto.RecommendResponse response = recipeService.toggleRecipeRecommend(userId, recipeId);
        if (response.getRecommendId() != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.ok(response);
        }
    }

    @PatchMapping("/update/{recipe-id}")
    public ResponseEntity patchRecipe(@PathVariable("recipe-id") long recipeId,
                                      @RequestBody RecipeDto.Patch requestBody) {   //레시피 수정
        requestBody.setRecipeId(recipeId);
        Recipe recipe = recipeMapper.patchToRecipe(requestBody);
        Recipe updatedRecipe = recipeService.updateRecipe(recipe);
        return new ResponseEntity(
                new SingleResponseDto<>(recipeMapper.recipeToResponse(updatedRecipe))
                        ,HttpStatus.OK);
    }

    @GetMapping("/find/{recipe-id}")
    public ResponseEntity getRecipe(@PathVariable("recipe-id") long recipeId) {    //레시피 상세 조회(조회수 증가)
        Recipe recipe = recipeService.findRecipe(recipeId);
        recipeService.incrementViewCount(recipe);

        return new ResponseEntity(
                new SingleResponseDto<>(recipeMapper.recipeToResponse(recipe))
                        , HttpStatus.OK);
    }

    @GetMapping("/findbyname")
    public ResponseEntity getRecipeSearch() {    //검색한 레시피 목록 조회
        return null;
    }

    @GetMapping("/find/main")
    public ResponseEntity getRecipesMain() {    //레시피 목록 조회(메인페이지에서 가진 재료를 바탕으로 검색)
        return null;
    }

    @GetMapping("/select")
    public ResponseEntity getRecipesSelected() {    //레시피 목록 조회(장바구니에서 선택한 재료를 바탕으로 검색)
        return null;
    }

    @GetMapping("/find/underbar")
    public ResponseEntity getRecipesUnderBar() {    //레시피 목록 조회(하단바 클릭)
        return null;
    }

    @DeleteMapping("/delete/{recipe-id}")
    public ResponseEntity deleteRecipe(@PathVariable("recipe-id") long recipeId) {    //레시피 삭제
        recipeService.deleteRecipe(recipeId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
