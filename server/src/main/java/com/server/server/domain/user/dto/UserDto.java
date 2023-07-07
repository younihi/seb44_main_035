package com.server.server.domain.user.dto;

public class UserDto {
    public static class Post {
        private String email;
        private String password;
    }

    public static class Response {
        private long userId;
    }
}
