package com.server.server.domain.recipe.service;

import com.server.server.domain.ingredient.entity.Ingredient;
import com.server.server.domain.ingredient.service.IngredientService;
import com.server.server.domain.recommend.service.RecommendService;
import com.server.server.domain.user.entity.User;
import com.server.server.domain.user.service.UserService;
import com.server.server.domain.recipe.dto.RecipeDto;
import com.server.server.domain.recipe.entity.Recipe;
import com.server.server.domain.recipe.repository.RecipeRepository;
import com.server.server.domain.recommend.entity.Recommend;
import com.server.server.domain.recommend.repository.RecommendRepository;
import com.server.server.global.exception.BusinessLogicException;
import com.server.server.global.exception.ExceptionCode;
import com.server.server.global.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RecipeService {
    @Autowired
    private final RecipeRepository recipeRepository;
    private final UserService userService;
    private final RecommendRepository recommendRepository;
    private final RecommendService recommendService;
    private final IngredientService ingredientService;
    private final S3Uploader s3Uploader;



    public Recipe createRecipe(Recipe recipe, MultipartFile recipeImage, List<MultipartFile> cookStepImage) {
        List<Ingredient> ingredients = ingredientService.saveAll(recipe.getIngredients());
        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredient.setRecipe(recipe);
        }
        recipe.setIngredients(ingredients);
        uploadImage(recipe, recipeImage, cookStepImage);
        return recipeRepository.save(recipe);
    }

    public void uploadImage(Recipe recipe, MultipartFile recipeImage, List<MultipartFile> cookStepImage) {
        String fileUrl = s3Uploader.putS3()
    }
    public Recipe updateRecipe(Recipe recipe) {
        Recipe findRecipe = findRecipe(recipe.getRecipeId());

        Optional.ofNullable(recipe.getRecipeName())
                .ifPresent(name -> findRecipe.setRecipeName(name));
        Optional.ofNullable(recipe.getRecipeImage())
                .ifPresent(image -> findRecipe.setRecipeImage(image));
        Optional.ofNullable(recipe.getRecipeIntro())
                .ifPresent(intro -> findRecipe.setRecipeIntro(intro));
        if (recipe.getCookStepContent().size() != 0) {
            findRecipe.setCookStepContent(recipe.getCookStepContent());
        }
        if (recipe.getCookStepImage().size() != 0) {
            findRecipe.setCookStepImage(recipe.getCookStepImage());
        }

        return recipeRepository.save(findRecipe);

    }

    public Recipe incrementViewCount(Recipe recipe) {
        recipe.setViews(recipe.getViews()+1);
        recipeRepository.save(recipe);
        return recipe;
    }
    public RecipeDto.RecommendResponse toggleRecipeRecommend(long userId, long recipeId) {
        User user = userService.findUser(userId);
        Recipe recipe = findRecipe(recipeId);
        List<Recommend> recommends = recipe.getRecommendList();

        Optional<Recommend> optionalRecommend = recommends.stream()
                .filter(recommend -> recommend.getUser().getUserId().equals(userId))
                .findFirst();
        RecipeDto.RecommendResponse response = new RecipeDto.RecommendResponse();
        response.setUserId(userId);
        response.setRecipeId(recipeId);

        if (optionalRecommend.isPresent()) {
            Recommend recommend = optionalRecommend.get();

            recommendService.deleteRecommend(user, recipe, userId, recipeId);

            recommendRepository.delete(recommend);

            response.setMessage("좋아요가 취소되었습니다.");
        } else {
            Recommend recommend = new Recommend(user, recipe);
            recipe.addRecommend(recommend);
            user.addRecommend(recommend);
            Recommend savedRecommend = recommendRepository.save(recommend);

            response.setRecommendId(savedRecommend.getRecommendId());
            response.setMessage("좋아요가 생성되었습니다.");
        }

        return response;
    }


    public void deleteRecipe(long recipeId) {
        recipeRepository.delete(findRecipe(recipeId));
    }
    public Recipe findRecipe(long recipeId) {
        return findVerifiedRecipe(recipeId);
    }

    public Recipe findVerifiedRecipe(long recipeId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        return optionalRecipe.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.RECIPE_NOT_FOUND));
    }

    // 레시피 제목으로 검색
    public Page<Recipe> searchRecipesByName(String recipeName, Pageable pageable) {
        return recipeRepository.findByRecipeNameContainingIgnoreCase(recipeName, pageable);
    }

    // 장바구니 속 재료로 검색
    public Page<Recipe> searchAllRecipesByIngredients(List<String> ingredients, Pageable pageable) {
        return recipeRepository.findByIngredientsIn(ingredients, pageable);
    }

    //냉장고 속 재료로 검색
    public Page<Recipe> searchRecipesByIngredients(List<String> ingredientNames, Pageable pageable) {
        return recipeRepository.findByIngredients_IngredientNameIn(ingredientNames, pageable);
    }

    //전체 레시피 조회(하단 바)
    public Page<Recipe> getAllRecipes(Pageable pageable) {
        return recipeRepository.findAll(pageable);
    }

}
