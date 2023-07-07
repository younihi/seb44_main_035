package com.server.server.domain.recipe.service;

import com.server.server.domain.member.entity.Member;
import com.server.server.domain.member.service.MemberService;
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
    private final MemberService memberService;
    private final RecommendRepository recommendRepository;

    public Recipe createRecipe(Recipe recipe) {
        Recipe savedRecipe = recipeRepository.save(recipe);

        return savedRecipe;
    }

    public RecipeDto.RecommendResponse toggleRecipeRecommend(long memberId, long recipeId) {
        Member member = memberService.findMember(memberId);
        Recipe recipe = findRecipe(recipeId);
        List<Recommend> recommends = recipe.getRecommendList();

        Optional<Recommend> optionalRecommend = recommends.stream()
                .filter(recommend -> recommend.getMember().getMemberId().equals(memberId))
                .findFirst();
        RecipeDto.RecommendResponse response = new RecipeDto.RecommendResponse();
        response.setMemberId(memberId);
        response.setRecipeId(recipeId);

        if (optionalRecommend.isPresent()) {
            Recommend recommend = optionalRecommend.get();

            recipe.removeRecommend(recommend);


            // ** 맴버와 레시피 안에 있는 recommend를 지우기

            recommendRepository.delete(recommend);

            response.setMessage("좋아요가 취소되었습니다.");
        } else {
            Recommend recommend = new Recommend(member, recipe);
            recipe.addRecommend(recommend);
            Recommend savedRecommend = recommendRepository.save(recommend);

            // ** 맴버와 레시피에 recommend 추가

            response.setRecommendId(savedRecommend.getRecommendId());
            response.setMessage("좋아요가 생성되었습니다.");
        }

        return response;
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
