package com.server.server.domain.recipe.service;

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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserService userService;
    private final RecommendRepository recommendRepository;
    private final RecommendService recommendService;

    public Recipe createRecipe(Recipe recipe) {
        Recipe savedRecipe = recipeRepository.save(recipe);

        return savedRecipe;
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
}
