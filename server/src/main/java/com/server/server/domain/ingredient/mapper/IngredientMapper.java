package com.server.server.domain.ingredient.mapper;
import com.server.server.domain.ingredient.dto.IngredientDto;
import com.server.server.domain.ingredient.entity.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IngredientMapper {
    Ingredient PostRecipeToIngredient(IngredientDto.PostRecipe requestBody);
    List<Ingredient> PostRecipeToIngredients(List<IngredientDto.PostRecipe> requestBody);
    Ingredient PostUserToIngredient(IngredientDto.PostUser requestBody);
    IngredientDto.Response ingredientToResponse(Ingredient ingredient);
    List<IngredientDto.Response> ingredientsToResponses(List<Ingredient> ingredients);

}
