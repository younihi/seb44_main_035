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
    @Mapping(target = "createdBy", source = "user.name")
    CommentDto.Response commentToCommentResponseDto(Comment comment);
    default List<CommentDto.Response> commentsToCommentResponseDtos(List<Comment> comments){
        List<CommentDto.Response> response=comments.stream()
                .map(comment->CommentDto.Response.builder()
                        .commentId(comment.getCommentId())
                        .commentContent(comment.getCommentContent())
//                        .createdAt(comment.getCreatedAt())
//                        .modifiedAt(comment.getModifiedAt())
                        .createdBy(comment.getUser().getName())
                        .createdBy(comment.getUser().getEmail())
                        .build())
                .collect(Collectors.toList());
        return response;
    }
}