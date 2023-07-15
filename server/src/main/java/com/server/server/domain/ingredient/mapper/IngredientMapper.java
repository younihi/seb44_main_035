package com.server.server.domain.ingredient.mapper;
import com.server.server.domain.ingredient.dto.IngredientDto;
import com.server.server.domain.ingredient.entity.Ingredient;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    Ingredient PostToIngredient(IngredientDto.Post requestBody);
    IngredientDto.Response ingredientToResponse(Ingredient ingredient);
    List<IngredientDto.Response> ingredientsToResponses(List<Ingredient> ingredients);

}
