package com.server.server.domain.comment.repository;

import com.server.server.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionException;

public interface CommentRepository extends JpaRepository<Comment,Long> {
//    List<Comment> findAllCommentsByRecipeId(Long recipeId);
    Optional<Comment> findByUserUserId(Long userId);
}