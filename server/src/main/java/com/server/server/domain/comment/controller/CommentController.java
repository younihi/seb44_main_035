package com.server.server.domain.comment.controller;

import com.server.server.domain.comment.dto.CommentDto;
import com.server.server.domain.comment.entity.Comment;
import com.server.server.domain.comment.mapper.CommentMapper;
import com.server.server.domain.comment.service.CommentService;
import com.server.server.global.response.MultiResponseDto;
import com.server.server.global.response.SingleResponseDto;
import com.server.server.global.utils.UriCreator;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/recipes/comment")
public class CommentController {

    private final static String COMMENT_DEFAULT_URL = "/recipes/comment";
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    public CommentController(CommentService commentService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @PostMapping("/create/{recipe-id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity postComment(@Valid @RequestBody CommentDto.Post post,
                                      @PathVariable("recipe-id") long recipeId) {    //댓글 등록
        Comment comment = commentMapper.commentPostToComment(post);
        Comment response = commentService.createComment(comment, recipeId);
        URI location = UriCreator.createUri(COMMENT_DEFAULT_URL, comment.getCommentId());

        return new ResponseEntity(new SingleResponseDto<>(commentMapper.commentToCommentResponseDto(response)),
                HttpStatus.CREATED);
    }

    @PatchMapping("/update/{comment-id}")
    public ResponseEntity patchComment(@Valid @RequestBody CommentDto.Patch patch,
                                       @Positive @PathVariable("comment-id") long commentId) {    //댓글 수정
        patch.setCommentId(commentId);
        Comment updatedComment = commentService.updateComment(commentMapper.commentPatchToComment(patch));
        return new ResponseEntity<>(new SingleResponseDto<>(commentMapper.commentToCommentResponseDto(updatedComment)), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{comment-id}")
    public ResponseEntity deleteComment(@PathVariable("comment-id") @Positive long commentId) {    //댓글 삭제
        commentService.deleteComment(commentId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


//    @GetMapping("/{comment-id}")
//    public ResponseEntity getComment(@PathVariable("comment-id") @Positive long commentId) {    //댓글 조회
//        Comment foundComment = commentService.findComment(commentId);
//        CommentDto.Response commentToCommentResponseDto = commentMapper.commentToCommentResponseDto(foundComment);
//        return ResponseEntity.ok(new SingleResponseDto<>(commentToCommentResponseDto));
//    }

//    @GetMapping
//    public ResponseEntity getComments(@RequestParam @Positive int page,
//                                      @RequestParam @Positive int size) {
//        Page<Comment> pageComments = commentService.findComments(page-1, size);
//        List<Comment> comments = pageComments.getContent();
//
//        return new ResponseEntity<>(
//                new MultiResponseDto<>(commentMapper.commentsToCommentResponseDtos(comments),pageComments), HttpStatus.OK);
//    }
}