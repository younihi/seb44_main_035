package com.server.server.domain.comment.dto;

public class CommentDto {
    public static class Post {
        private String commentContent;
    }

    public static class Patch {
        private String commentContent;
    }

    public static class Response {
        private long memberId;
        private long recipeId;
        private long commentId;
        private String commentContent;
    }

}
