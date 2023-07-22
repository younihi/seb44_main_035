package com.server.server.domain.ingredient.service;

import com.server.server.domain.ingredient.dto.IngredientDto;
import com.server.server.domain.ingredient.entity.Ingredient;
import com.server.server.domain.ingredient.repository.IngredientRepository;
import com.server.server.domain.recipe.entity.Recipe;
import com.server.server.domain.user.entity.User;
import com.server.server.domain.user.service.UserService;
import com.server.server.global.exception.BusinessLogicException;
import com.server.server.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final UserService userService;

    @Transactional
    public Ingredient addIngredient(Ingredient ingredient, long userId){
        Ingredient findIngredient = findVerifiedIngredient(ingredient.getIngredientName(), userId);
        Ingredient userIngredient = new Ingredient(findIngredient.getIngredientName());
        User user = userService.findUser(userId);
        user.addIngredient(userIngredient);

        return ingredientRepository.save(userIngredient);
    }
    public List<Ingredient> saveAll(List<Ingredient> ingredients) {
        return ingredientRepository.saveAll(ingredients);
    }
    @Transactional
    public void deleteIngredient(long ingredientId, long userId) {
        User user = userService.findUser(userId);
        Ingredient ingredient = findIngredient(ingredientId);
        user.removeIngredient(ingredient);

        ingredientRepository.delete(ingredient);
    }
    public void deleteIngredient(long ingredientId) {
        ingredientRepository.delete(findIngredient(ingredientId));
    }

    @Transactional
    public Ingredient findIngredient(long ingredientId) {
        return findVerifiedIngredient(ingredientId);
    }
    public Ingredient findVerifiedIngredient(long ingredientId){
        Optional<Ingredient> ingredient = ingredientRepository.findById(ingredientId);
        return  ingredient.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.INGREDIENT_NOT_FOUND));
    }
    public Ingredient findVerifiedIngredient(String ingredientName, long userId){
        try {
            List<Ingredient> ingredients = ingredientRepository.findByIngredientName(ingredientName);
            Ingredient ingredient = ingredients.get(0);
            verifyExistIngredient(ingredient, userId);
            return ingredient;
        } catch (BusinessLogicException e) {
            throw new BusinessLogicException(ExceptionCode.INGREDIENT_EXISTS);
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.INGREDIENT_NOT_FOUND);
        }
    }

    //    @Transactional(readOnly = true) //트랜잭션 범위는 유지 /  기능을 조회함으로써 조회속도 개선
//    public List<IngredientDto.Response> findIngredients(){
//
//        return  ingredientRepository.findAll().stream()
//                .map(IngredientDto.Response::new)
//                .collect(Collectors.toList());
//    }
    public void verifyExistIngredient(Ingredient ingredient, long userId) {
        User user = userService.findUser(userId);
        for (Ingredient ingre : user.getIngredientList()) {
            if (ingre.getIngredientName().equals(ingredient.getIngredientName())) {
                throw new BusinessLogicException(ExceptionCode.INGREDIENT_EXISTS);
            }
        }
    }
    public Page<Ingredient> findUserIngredient(long userId, int page, int size) {
        User findUser = userService.findUser(userId);
        Pageable pageable = PageRequest.of(page, size,Sort.by("ingredientId"));
        Page<Ingredient> ingredients = ingredientRepository.findAllByUser(findUser, pageable);
        return ingredients;
    }
}
