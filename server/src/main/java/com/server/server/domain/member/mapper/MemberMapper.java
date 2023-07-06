package com.server.server.domain.member.mapper;

import com.server.server.domain.comment.dto.CommentByMemberDto;
import com.server.server.domain.member.dto.*;
import com.server.server.domain.member.entity.Member;
import com.server.server.domain.recipe.dto.RecipeByMemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class MemberMapper {
    public Member memberPostToMember(MemberPostDto memberPostDto) {
        if (memberPostDto == null) {
            return null;
        }
        return Member.builder()
                .username(memberPostDto.getUsername())
                .email(memberPostDto.getEmail())
                .password(memberPostDto.getPassword())
                .image("")
                .build();
    }

    public Member memberPatchToMember(MemberEditDto memberEditDto) {

        return Member.builder()
                .memberId(memberEditDto.getMemberId())
                .username(memberEditDto.getUsername())
                .image(memberEditDto.getImage())
                .build();
    }

    public MemberPostResponseDto memberToMemberResponse(Member member) {
        if (member == null) {
            return null;
        }

        return MemberPostResponseDto.builder()
                .memberId(member.getMemberId())
                .username(member.getUsername())
                .email(member.getEmail())
                .build();
    }

    public MemberProfileResponseDto memberToMemberProfileResponse(Member member) {
        return MemberProfileResponseDto.builder()
                .memberId(member.getMemberId())
                .profileCreatedAt(member.getCreatedAt())
                .username(member.getUsername())
                .email(member.getEmail())
                .image(member.getImage())
                //.totalMyRecipes(member.getRecipeList().size())
                //.totalMyComments(member.getCommentsList().size())
                .profilePosts(memberToProfilePostsResponseDtoList(member))
                .build();
    }

    public List<MemberToUserPageResponseDto> memberUserToResponseDto(List<Member> members) {
        if (members == null) {
            return null;
        }

        return members.stream()
                .map(member -> {
                    return MemberToUserPageResponseDto.builder()
                            .memberId(member.getMemberId())
                            .username(member.getUsername())
                            .image(member.getImage())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public MemberLoginResponseDto memberToMemberLoginResponseDto(Member member) {
        return MemberLoginResponseDto.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .image(member.getImage())
                .build();
    }

    public List<MemberProfilePostsResponseDto> memberToProfilePostsResponseDtoList(Member member){

        MemberProfilePostsResponseDto memberProfilePostsResponseDto = MemberProfilePostsResponseDto.builder()
                .recipes(memberToRecipeByMemberDtoList(member))
                .comments(memberToCommentsByMemberDtoList(member))
                .build();
        List<MemberProfilePostsResponseDto> memberProfilePostsResponseDtoList = new ArrayList<>(List.of(memberProfilePostsResponseDto));
        return memberProfilePostsResponseDtoList;
    }

    public List<RecipeByMemberDto> memberToRecipeByMemberDtoList(Member member){
        return member.getRecipeList().stream()
                .map(
                        question -> {
                            return RecipeByMemberDto.builder()
                                    .recipeId(question.getRecipeId())
                                    .recipeTitle(question.getRecipeTitle())
                                    .recipeCreatedAt(question.getCreatedAt())
                                    .recipeVoteCount(question.getRecipeLikeCount())
                                    .build();
                        })
                .collect(Collectors.toList());
    }

    public List<AnswerByMemberDto> memberToAnswerByMemberDtoList(Member member){
        return member.getAnswersList().stream()
                .map(
                        answer -> {
                            return AnswerByMemberDto.builder()
                                    .answerId(answer.getAnswerId())
                                    .answerTitle(answer.getQuestion().getQuestionTitle())
                                    .answerCreatedAt(answer.getCreatedAt())
                                    .answerVoteCount(answer.getAnswerVoteCount())
                                    .build();
                        })
                .collect(Collectors.toList());
    }

}
