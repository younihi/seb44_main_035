package com.server.server.domain.user.service;


import com.server.server.domain.user.dto.SignupForm;
import com.server.server.domain.comment.entity.Comment;
import com.server.server.domain.recipe.entity.Recipe;
import com.server.server.domain.recommend.entity.Recommend;
import com.server.server.domain.user.entity.User;
import com.server.server.domain.user.repository.UserRepository;
import com.server.server.global.exception.BusinessLogicException;
import com.server.server.global.exception.ExceptionCode;
import com.server.server.global.security.config.jwt.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserService {
    private final BCryptPasswordEncoder encoder;
    private final UserRepository repository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(BCryptPasswordEncoder encoder, UserRepository repository, AuthenticationManagerBuilder authenticationManagerBuilder, JwtTokenProvider jwtTokenProvider) {
        this.encoder = encoder;
        this.repository = repository;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String login(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        // 검증
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 검증된 인증 정보로 JWT 토큰 생성
        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    public Long signup(SignupForm signupForm) {
        boolean check = checkEmailExists(signupForm.getEmail());

        if (check) {
            throw new IllegalArgumentException("이미 존재하는 유저입니다.");
        }

        String encPwd = encoder.encode(signupForm.getPassword());

        User user = repository.save(signupForm.toEntity(encPwd));

        if(user!=null) {
            return user.getUserId();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public boolean checkEmailExists(String email) {
        return repository.existsUsersByEmail(email);
    }


    public User createUser(User user) {
        return repository.save(user);
    }
    public User findUser(long userId) {
        Optional<User> optionalUser = repository.findById(userId);
        return optionalUser.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }
    public User saveUser(Long userid) {
        User user = new User();
        user.setUserid(userid);


        return repository.save(user);
    }

    public String generateTemporaryUserId() {
        return UUID.randomUUID().toString();
    }

    public Page<Recipe> findUserRecommendRecipe(long userId, Pageable pageable) {
        User user = findUser(userId);
        List<Recipe> recipeList = new ArrayList<>();

        for (Recommend recommend : user.getRecommendList()) {
            recipeList.add(recommend.getRecipe());
        }
        return convertToPage(recipeList, pageable);
    }
    public Page<Recipe> findUserRecipe(long userId, Pageable pageable) {
        User user = findUser(userId);
        List<Recipe> recipeList = new ArrayList<>();

        for (Recipe recipe : user.getRecipeList()) {
            recipeList.add(recipe);
        }
        return convertToPage(recipeList, pageable);
    }
    public Page<Recipe> findUserCommentRecipe(long userId, Pageable pageable) {
        User user = findUser(userId);
        List<Recipe> recipeList = new ArrayList<>();

        for (Comment comment : user.getCommentList()) {
            recipeList.add(comment.getRecipe());
        }
        return convertToPage(recipeList, pageable);
    }
    public Page<Recipe> convertToPage(List<Recipe> recipeList, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Recipe> pagedRecipes;

        if (recipeList.size() < startItem) {
            pagedRecipes = new ArrayList<>();
        } else {
            int toIndex = Math.min(startItem + pageSize, recipeList.size());
            pagedRecipes = recipeList.subList(startItem, toIndex);
        }

        return new PageImpl<>(pagedRecipes, pageable, recipeList.size());
    }
}
