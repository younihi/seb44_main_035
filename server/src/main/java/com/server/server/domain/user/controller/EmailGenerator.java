package com.server.server.domain.user.controller;

import java.util.UUID;

public class EmailGenerator {

    public static String generateRandomEmail() {
        // UUID를 사용하여 랜덤한 이메일 생성
        String randomUUID = UUID.randomUUID().toString();

        // 이메일 형식에 맞게 조합
        String email = "user" + randomUUID.substring(0, 8) + "@example.com";

        return email;
    }
}
