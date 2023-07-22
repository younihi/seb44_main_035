package com.server.server.domain.comment.service;

import com.server.server.domain.comment.entity.Comment;
import com.server.server.domain.comment.repository.CommentRepository;
import com.server.server.domain.recipe.entity.Recipe;
import com.server.server.domain.recipe.service.RecipeService;
import com.server.server.global.exception.BusinessLogicException;
import com.server.server.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final RecipeService recipeService;


    public Comment createComment(Comment comment, Long recipeId) {
        Recipe recipe = recipeService.findRecipe(recipeId);
        recipe.addComment(comment);
        return commentRepository.save(comment);
    }

    public Comment updateComment(Comment comment) {
        Comment foundComment = findComment(comment.getCommentId());
        foundComment.setCommentContent(comment.getCommentContent());

        return commentRepository.save(foundComment);
    }

    public Comment findComment(long commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        return commentOptional.orElseThrow(()-> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
    }

    public Page<Comment> findComments(int page, int size) {
        return commentRepository.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    public void deleteComment(long commentId) {
        Comment foundComment = findComment(commentId);

        commentRepository.delete(foundComment);
    }
}
//    public Comment createComment(Comment comment, Long recipeId) {
//        comment.setRecipe(recipeService.findVerifiedRecipe(recipeId));
////        comment.setUser(authenticationUser());
//        return commentRepository.save(comment);
//    }
//
//    public Comment updateComment(Comment comment) {
//        Comment foundComment = findComment(comment.getCommentId());
//
////        if (authenticationUser().getUserId() != foundComment.getUser().getUserId()) {
////            throw new BusinessLogicException(ExceptionCode.CANNOT_CHANGE_COMMENT);
////        }
////
////        Comment updatedComment = beanUtils.copyNonNullProperties(comment, foundComment);
//
//        return commentRepository.save(foundComment);
//    }
//
//    public Comment findComment(long commentId) {
//        Optional<Comment> commentOptional = commentRepository.findById(commentId);
//        return commentOptional.orElseThrow(()-> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
//    }
//
//    public Page<Comment> findComments(int page, int size) {
//        return commentRepository.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()));
//    }
//
//    public void deleteComment(long commentId) {
//        Comment foundComment = findComment(commentId);
//
////        if (authenticationUser().getUserId() != foundComment.getUser().getUserId()) {
////            throw new BusinessLogicException(ExceptionCode.CANNOT_CHANGE_COMMENT);
////        }
//
//        commentRepository.delete(foundComment);
//    }
//
////    private User authenticationUser() {
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        //현재 로그인한 사용자 이메일
////        String username = (String) authentication.getPrincipal();
////
////        //로그인한 ID(이메일)로 User를 찾아서 반환
////        return userService.findUser(username);
////    }
////
////    public List<CommentDto.CommentResponseDtoForUser> getCommentByUserId(Long userId) {
////        Optional<Comment> comments = commentRepository.findByUserUserId(userId);
////        List<CommentDto.CommentResponseDtoForUser> answerDtos = new ArrayList<>();
////
////        for (Comment comment : comments) {
////            CommentDto.CommentResponseDtoForUser answerDto = new CommentDto.CommentResponseDtoForUser(
////                    comment.getCommentId(),
////                    comment.getCommentContent(),
////                    comment.getCreatedAt(),
////                    comment.getModifiedAt(),
////                    comment.getUser().getName()
////            );
////            answerDtos.add(answerDto);
////        }
////
////        return answerDtos;
////    }
//}
