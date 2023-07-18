package com.server.server.domain.user.service;

import com.server.server.domain.comment.entity.Comment;
import com.server.server.domain.recipe.entity.Recipe;
import com.server.server.domain.recommend.entity.Recommend;
import com.server.server.domain.user.entity.User;
import com.server.server.domain.user.repository.UserRepository;
import com.server.server.global.exception.BusinessLogicException;
import com.server.server.global.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
    public User findUser(long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
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
