package com.server.server.domain.ingredient.service;

import com.server.server.domain.ingredient.dto.IngredientDto;
import com.server.server.domain.ingredient.entity.Ingredient;
import com.server.server.domain.ingredient.repository.IngredientRepository;
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
        Ingredient findIngredient = findVerifiedIngredient(ingredient.getIngredientName());
        User user = userService.findUser(userId);
        user.addIngredient(findIngredient);

        return ingredientRepository.save(findIngredient);
    }
    @Transactional
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
    public Ingredient findVerifiedIngredient(String ingredientName){
        Optional<Ingredient> ingredient = ingredientRepository.findByName(ingredientName);
        return  ingredient.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.INGREDIENT_NOT_FOUND));
    }
//    @Transactional(readOnly = true) //트랜잭션 범위는 유지 /  기능을 조회함으로써 조회속도 개선
//    public List<IngredientDto.Response> findIngredients(){
//
//        return  ingredientRepository.findAll().stream()
//                .map(IngredientDto.Response::new)
//                .collect(Collectors.toList());
//    }

    public Page<Ingredient> findUserIngredient(long userId, int page, int size) {
        User findUser = userService.findUser(userId);
        Pageable pageable = PageRequest.of(page, size,Sort.by("ingredientId").descending());
        Page<Ingredient> ingredients = ingredientRepository.findAllByUser(findUser, pageable);
        return ingredients;
    }
}
