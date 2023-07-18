package com.server.server.domain.recipe.controller;

import com.server.server.domain.ingredient.entity.Ingredient;
import com.server.server.domain.ingredient.mapper.IngredientMapper;
import com.server.server.domain.recipe.dto.RecipeDto;
import com.server.server.domain.recipe.entity.Recipe;
import com.server.server.domain.recipe.mapper.RecipeMapper;
import com.server.server.domain.recipe.service.RecipeService;
import com.server.server.global.response.MultiResponseDto;
import com.server.server.global.response.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/recipes")
@Validated
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeMapper recipeMapper;
    private final RecipeService recipeService;
    private final IngredientMapper ingredientMapper;


    //레시피 등록
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity postRecipe(@RequestPart(value = "recipeImage", required = false) MultipartFile recipeImage,
                                     @RequestPart(value = "cookStepImage", required = false) List<MultipartFile> cookStepImage,
                                     @RequestPart(value = "recipe") RecipeDto.Post requestBody) {
        List<Ingredient> ingredients = ingredientMapper.PostRecipeToIngredients(requestBody.getIngredients());
        Recipe recipe = recipeMapper.postToRecipe(requestBody, ingredients);
        Recipe savedRecipe = recipeService.createRecipe(recipe, recipeImage, cookStepImage);

        return new ResponseEntity<>(new SingleResponseDto(recipeMapper.recipeToPostResponse(savedRecipe)), HttpStatus.CREATED);
    }

    //레시피 추천(토글 형식)
    @PostMapping("/recommend/{recipe-id}/{user-id}")
    public ResponseEntity toggleRecipeRecommend(@PathVariable("recipe-id") long recipeId,
                                                @PathVariable("user-id") long userId) {
        RecipeDto.RecommendResponse response = recipeService.toggleRecipeRecommend(userId, recipeId);
        if (response.getRecommendId() != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.ok(response);
        }
    }

    //레시피 수정
    @PatchMapping("/update/{recipe-id}")
    public ResponseEntity patchRecipe(@PathVariable("recipe-id") long recipeId,
                                      @RequestPart(value = "recipeImage", required = false) MultipartFile recipeImage,
                                      @RequestPart(value = "cookStepImage", required = false) List<MultipartFile> cookStepImage,
                                      @RequestPart(value = "recipe", required = false) RecipeDto.Patch requestBody) {
        if (requestBody != null) {
            requestBody.setRecipeId(recipeId);
        }
        else {
            requestBody = new RecipeDto.Patch(recipeId);
        }
        List<Ingredient> ingredients = ingredientMapper.PostRecipeToIngredients(requestBody.getIngredients());
        Recipe recipe = recipeMapper.patchToRecipe(requestBody, ingredients);
        Recipe updatedRecipe = recipeService.updateRecipe(recipe, recipeImage, cookStepImage);
        return new ResponseEntity(
                new SingleResponseDto<>(recipeMapper.recipeToResponse(updatedRecipe))
                        ,HttpStatus.OK);
    }

    //레시피 상세 조회
    @GetMapping("/find/{recipe-id}")
    public ResponseEntity getRecipe(@PathVariable("recipe-id") long recipeId) {
        Recipe recipe = recipeService.findRecipe(recipeId);
        recipeService.incrementViewCount(recipe);

        return new ResponseEntity(
                new SingleResponseDto<>(recipeMapper.recipeToResponse(recipe))
                        , HttpStatus.OK);
    }

    //레시피 제목으로 검색
    @GetMapping("/findbyname")
    public ResponseEntity<List<RecipeDto.ListResponse>> getRecipeSearch(
            @RequestParam("recipe-name") String recipeName,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Recipe> recipePage = recipeService.searchRecipesByName(recipeName, pageable);

        List<RecipeDto.ListResponse> responseList = recipeMapper.recipesToResponseList(recipePage.getContent());

        return new ResponseEntity(new MultiResponseDto<>(responseList, recipePage),HttpStatus.OK );
    }

    //냉장고 속 재료로 검색(하나 이상 포함되면 검색)
    @GetMapping("/find/main")
    public ResponseEntity<List<RecipeDto.ListResponse>> getRecipesMain(
            @RequestParam List<String> ingredients,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Recipe> recipePage = recipeService.searchRecipesByIngredients(ingredients, pageable);
        List<RecipeDto.ListResponse> responseList = recipeMapper.recipesToResponseList(recipePage.getContent());

        return new ResponseEntity(new MultiResponseDto<>(responseList, recipePage),HttpStatus.OK );
    }

    //장바구니에 추가된 재료로 검색(들어온 재료 모두로 검색)
    @GetMapping("/select")
    public ResponseEntity<List<RecipeDto.ListResponse>> getRecipesSelected(
            @RequestParam List<String> ingredients,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Recipe> recipePage = recipeService.searchAllRecipesByIngredients(ingredients, pageable);
        List<RecipeDto.ListResponse> responseList = recipeMapper.recipesToResponseList(recipePage.getContent());

        return new ResponseEntity(new MultiResponseDto<>(responseList, recipePage),HttpStatus.OK );
    }

    //레시피 목록 조회(하단 바 클릭)
    @GetMapping("/find/underbar")
    public ResponseEntity<List<RecipeDto.ListResponse>> getRecipesUnderBar(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("recommendCount").descending());
        Page<Recipe> recipePage = recipeService.getAllRecipes(pageable);

        List<RecipeDto.ListResponse> responseList = recipeMapper.recipesToResponseList(recipePage.getContent());

        return new ResponseEntity(new MultiResponseDto<>(responseList, recipePage),HttpStatus.OK );
    }

    //레시피 삭제
    @DeleteMapping("/delete/{recipe-id}")
    public ResponseEntity deleteRecipe(@PathVariable("recipe-id") long recipeId) {
        recipeService.deleteRecipe(recipeId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
