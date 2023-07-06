package com.server.server.domain.member.dto;

import com.server.server.domain.comment.dto.CommentByMemberDto;
import com.server.server.domain.recipe.dto.RecipeByMemberDto;
import com.server.server.domain.recipe.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class MemberProfileResponseDto {
    @Positive
    private Long memberId;
    private LocalDateTime profileCreatedAt;
    @Pattern(regexp = "^\\S+(\\s?\\S+)*$")
    @NotBlank(message = "회원 이름은 공백이 아니여야한다.")
    private String username;
    @Email
    @Pattern(regexp = "\\w+@\\w+\\.\\w+(\\.\\w+)?")
    @NotBlank(message = "이메일은 공백이 아니여야 합니다.")
    private String email;
    private String title;
    private String image;
    private long totalMyRecipes;
    private long totalMyComments;
    private List<MemberProfilePostsResponseDto> profilePosts;
}
