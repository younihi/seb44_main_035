package com.server.server.domain.comment.mapper;

import com.server.server.domain.comment.dto.CommentDto;
import com.server.server.domain.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    Comment commentPostToComment(CommentDto.Post requestBody);
    Comment commentPatchToComment(CommentDto.Patch requestBody);
    default CommentDto.Response commentToCommentResponseDto(Comment comment) {
        CommentDto.Response response = new CommentDto.Response();
        response.setCommentId(comment.getCommentId());
        response.setRecipeId(comment.getRecipe().getRecipeId());
        response.setUserId(comment.getUser().getUserId());
        response.setCommentContent(comment.getCommentContent());
        response.setCreatedAt(comment.getCreatedAt());
        response.setModifiedAt(comment.getModifiedAt());
        return response;
    }
}