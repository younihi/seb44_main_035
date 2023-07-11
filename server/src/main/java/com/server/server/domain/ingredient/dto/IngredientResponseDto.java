package com.server.server.domain.ingredient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class IngredientResponseDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public  static class Ingredient {

        private Long ingredientId;
        private String ingredientName;
    }

}
