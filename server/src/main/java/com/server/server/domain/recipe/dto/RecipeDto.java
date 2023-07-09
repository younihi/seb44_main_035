package com.server.server.domain.recipe.dto;

import com.server.server.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class RecipeDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        private String recipeName;
        private String recipeImage;
        private String recipeIntro;
        private List<String> cookStepContent;
        private List<String> cookStepImage;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Patch {
        private long recipeId;
        private String recipeName;
        private String recipeImage;
        private String recipeIntro;
        private List<String> cookStepContent;
        private List<String> cookStepImage;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private long recipeId;
        private String recipeName;
        private String recipeImage;
        private String recipeIntro;
        private List<String> cookStepContent;
        private List<String> cookStepImage;
        private int views;
        private int recommendCount;
        private List<Comment> comments;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListResponse {
        private long recipeId;
        private String recipeName;
        private String recipeImage;
        private int views;
        private int recommendCount;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecommendResponse {
        private Long userId;
        private Long recipeId;
        private Long recommendId;
        private String message;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostResponse {
        private long recipeId;
    }
}
