package com.server.server.domain.recipe.dto;

import com.server.server.domain.comment.entity.Comment;
import lombok.Setter;

import java.util.List;

public class RecipeDto {
    public static class Post {
        private String recipeName;
        private String recipeImage;
        private String recipeIntro;
        private List<String> cookStepContent;
        private List<String> cookStepImage;
    }

    public static class Patch {
        private String recipeName;
        private String recipeImage;
        private String recipeIntro;
        private List<String> cookStepContent;
        private List<String> cookStepImage;
    }

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

    public static class ListResponse {
        private long recipeId;
        private String recipeName;
        private String recipeImage;
        private int views;
        private int recommendCount;
    }
    @Setter
    public static class RecommendResponse {
        private long memberId;
        private long recipeId;
        private long recommendId;
        private String message;
    }
}
