package com.server.server.domain.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipes/comment")
public class CommentController {


    @PostMapping
    public ResponseEntity postComment() {    //댓글 등록
        return null;
    }

    @PatchMapping("/{comment-id}")
    public ResponseEntity patchComment() {    //댓글 수정
        return null;
    }

    @DeleteMapping
    public ResponseEntity deleteComment() {    //댓글 삭제
        return null;
    }
}
