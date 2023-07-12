package com.server.server.domain.recipe.controller;

import com.server.server.domain.recipe.dto.RecipeDto;
import com.server.server.domain.recipe.entity.Recipe;
import com.server.server.domain.recipe.mapper.RecipeMapper;
import com.server.server.domain.recipe.service.RecipeService;
import com.server.server.global.response.MultiResponseDto;
import com.server.server.global.response.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

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
    public ResponseEntity getRecipe(@PathVariable("recipe-id") long recipeId) {    //레시피 상세 조회
        Recipe recipe = recipeService.findRecipe(recipeId);

        return new ResponseEntity(
                new SingleResponseDto<>(recipeMapper.recipeToResponse(recipe))
                        , HttpStatus.OK);
    }

    //레시피 제목으로 검색
    @GetMapping("/findbyname/{recipe-name}")
    public ResponseEntity getRecipeSearch(@PathVariable("recipe-name") String recipeName) {
        List<Recipe> recipes = recipeService.searchRecipesByName(recipeName);

        MultiResponseDto<RecipeDto.ListResponse> responseDto = new MultiResponseDto<>(
                recipeMapper.recipesToResponseList(recipes),
                null // 페이지 정보가 없을 경우에는 null로 전달하거나 필요에 따라 적절한 값을 전달(page -> 무한스크롤?)
        );

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    //냉장고 속 재료로 검색
    @GetMapping("/find/main")
    public ResponseEntity getRecipesMain(@RequestParam List<String> ingredients) {
        List<Recipe> recipes = recipeService.searchRecipesByIngredients(ingredients);

        MultiResponseDto<RecipeDto.ListResponse> responseDto = new MultiResponseDto<>(
                recipeMapper.recipesToResponseList(recipes),
                null // 페이지 정보가 없을 경우에는 null로 전달하거나 필요에 따라 적절한 값을 전달(page -> 무한스크롤?)
        );

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    //장바구니에 추가된 재료로 검색(냉장고 검색이랑 동일? -> api 통일해야할지...)
    @GetMapping("/select")
    public ResponseEntity getRecipesSelected(@RequestParam List<String> ingredients) {    //레시피 목록 조회(장바구니에서 선택한 재료를 바탕으로 검색)
        List<Recipe> recipes = recipeService.searchRecipesByIngredients(ingredients);

        MultiResponseDto<RecipeDto.ListResponse> responseDto = new MultiResponseDto<>(
                recipeMapper.recipesToResponseList(recipes),
                null // 페이지 정보가 없을 경우에는 null로 전달하거나 필요에 따라 적절한 값을 전달(page -> 무한스크롤?)
        );

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
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
