package com.server.server.domain.ingredient.dto;

public class IngredientDto {
    public static class Post{
        private String ingredientName;
    }

    public static class Patch {
        private String ingredientName;
    }

    public static class Response {
        private String ingredientName;
    }
}
