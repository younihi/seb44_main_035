package com.server.server.domain.recipe.mapper;

import com.server.server.domain.recipe.dto.RecipeDto;
import com.server.server.domain.recipe.entity.Recipe;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RecipeMapper {
    default Recipe postToRecipe(RecipeDto.Post post){
        Recipe recipe = new Recipe();
        recipe.setRecipeName(post.getRecipeName());
        recipe.setRecipeIntro(post.getRecipeIntro());
        recipe.setRecipeImage(post.getRecipeImage());
        if ( recipe.getCookStepContent() != null ) {
            List<String> list = post.getCookStepContent();
            if ( list != null ) {
                recipe.getCookStepContent().addAll( list );
            }
        }
        if ( recipe.getCookStepImage() != null ) {
            List<String> list1 = post.getCookStepImage();
            if ( list1 != null ) {
                recipe.getCookStepImage().addAll( list1 );
            }
        }
        return recipe;
    }
    Recipe patchToRecipe(RecipeDto.Patch patch);
    RecipeDto.Response recipeToResponse(Recipe recipe);
    default List<RecipeDto.ListResponse> recipeToResponseList(List<Recipe> recipes){
        List<RecipeDto.ListResponse> list = new ArrayList<>();
        for (Recipe recipe : recipes) {
            RecipeDto.ListResponse response = new RecipeDto.ListResponse();
            response.setRecipeId(recipe.getRecipeId());
            response.setRecipeName(recipe.getRecipeName());
            response.setRecipeImage(recipe.getRecipeImage());
            response.setViews(recipe.getViews());
            response.setRecommendCount(recipe.getRecommendCount());
        }
        return list;
    }
    RecipeDto.PostResponse recipeToPostResponse(Recipe recipe);

    default List<RecipeDto.ListResponse> recipesToResponseList(List<Recipe> recipes) {
        return recipes.stream()
                .map(this::recipeToListResponse)
                .collect(Collectors.toList());
    }


    RecipeDto.ListResponse recipeToListResponse(Recipe recipe);

}
