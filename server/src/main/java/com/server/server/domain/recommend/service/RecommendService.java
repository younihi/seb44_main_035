package com.server.server.domain.recommend.service;

import com.server.server.domain.recipe.entity.Recipe;
import com.server.server.domain.recipe.service.RecipeService;
import com.server.server.domain.recommend.entity.Recommend;
import com.server.server.domain.recommend.repository.RecommendRepository;
import com.server.server.domain.user.entity.User;
import com.server.server.domain.user.service.UserService;
import com.server.server.global.exception.BusinessLogicException;
import com.server.server.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RecommendService {
    private final UserService userService;
    private final RecipeService recipeService;
    private final RecommendRepository recommendRepository;
    public void createRecommend(long userId, long recipeId) {

        User findUser = userService.findUser(userId);
        Recipe findRecipe = recipeService.findRecipe(recipeId);

        Recommend recommend = new Recommend();
        recommend.setUser(findUser);
        recommend.setRecipe(findRecipe);

        findUser.addRecommend(recommend);
        findRecipe.addRecommend(recommend);

        recommendRepository.save(recommend);
    }
    public void deleteRecommend(long userId, long recipeId ){
        Recommend findRecommend = findVerifiedRecommend(userId, recipeId);
        User findUser = userService.findUser(userId);
        Recipe findRecipe = recipeService.findRecipe(recipeId);

        findUser.removeRecommend(findRecommend);
        findRecipe.removeRecommend(findRecommend);

        recommendRepository.delete(findRecommend);

    }

    public Recommend findVerifiedRecommend(long userId, long recipeId) {
        Optional<Recommend> optionalRecommend = recommendRepository.findByUserIdAndRecipeId(userId, recipeId);

        return optionalRecommend.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.RECOMMEND_NOT_FOUND));
    }
}
