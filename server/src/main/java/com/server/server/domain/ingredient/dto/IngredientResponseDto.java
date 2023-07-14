package com.server.server.domain.ingredient.dto;

import com.server.server.domain.ingredient.entity.Ingredient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class IngredientResponseDto {

        private Long ingredientId;
        private String ingredientName;

    public IngredientResponseDto(Ingredient ingredient) {
        this.ingredientId = ingredient.getIngredientId();
        this.ingredientName = ingredient.getIngredientName();
    }
}


