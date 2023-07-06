package com.server.server.domain.member.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
@Data
@Builder
public class MemberEditDto {
    @Positive
    private Long memberId;
    private String profileCreatedAt;
    @Pattern(regexp = "^\\S+(\\s?\\S+)*$")
    @NotBlank( message = "회원 이름은 공백이 아니여야한다.")
    private String username;
    @Email
    @Pattern(regexp = "\\w+@\\w+\\.\\w+(\\.\\w+)?")
    @NotBlank(message = "이메일은 공백이 아니여야 합니다.")
    private String email;
    private String image;
    private String title;
}
