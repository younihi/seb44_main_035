package com.server.server.domain.recipe.mapper;

import com.server.server.domain.recipe.dto.RecipeDto;
import com.server.server.domain.recipe.entity.Recipe;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecipeMapper {
    Recipe postToRecipe(RecipeDto.Post post);
    Recipe patchToRecipe(RecipeDto.Patch patch);
    RecipeDto.Response recipeToResponse(Recipe recipe);
    RecipeDto.ResponseList recipeToResponseList(List<Recipe> recipes);
}
