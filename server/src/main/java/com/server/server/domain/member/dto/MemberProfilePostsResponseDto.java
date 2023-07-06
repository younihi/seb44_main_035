package com.server.server.domain.member.dto;

import com.server.server.domain.recipe.dto.recipeByMemberDto;
import com.server.server.domain.comment.dto.commentByMemberDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MemberProfilePostsResponseDto {
    private List<recipeByMemberDto> recipes;
    private List<commentByMemberDto> comments;
}
