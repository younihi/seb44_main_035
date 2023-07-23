package com.server.server.domain.comment.service;

import com.server.server.domain.comment.dto.CommentDto;
import com.server.server.domain.comment.entity.Comment;
import com.server.server.domain.comment.repository.CommentRepository;
import com.server.server.domain.recipe.entity.Recipe;
import com.server.server.domain.recipe.service.RecipeService;
import com.server.server.domain.user.entity.User;
import com.server.server.domain.user.service.UserService;
import com.server.server.global.exception.ExceptionCode;
import com.server.server.global.utils.CustomBeanUtils;
import com.server.server.global.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final RecipeService recipeService;
    private final UserService userService;

    public Comment createComment(Comment comment, Long recipeId, Long userId) {
        Recipe recipe = recipeService.findRecipe(recipeId);
        User user = userService.findUser(userId);
        recipe.addComment(comment);
        user.addComment(comment);
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