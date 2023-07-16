package com.server.server.domain.ingredient.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class IngredientDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostRecipe {
        private String ingredientName;
        private String quantity;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostUser {
        private String ingredientName;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private long ingredientId;
        private String ingredientName;
        private String quantity;
    }
}
