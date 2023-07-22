package com.server.server.global.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member exists"),
    RECIPE_NOT_FOUND(404, "Recipe not found"),
    RECOMMEND_NOT_FOUND(404, "Recommend not found"),
    CANNOT_CHANGE_COMMENT(403, "해당 댓글에 대한 (수정/삭제) 권한이 없습니다."),
    COMMENT_NOT_FOUND(404, "해당 댓글을 찾을 수 없습니다.");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}