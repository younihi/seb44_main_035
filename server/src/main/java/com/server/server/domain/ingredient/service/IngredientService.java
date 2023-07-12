package com.server.server.domain.ingredient.service;

import com.server.server.domain.ingredient.dto.IngredientResponseDto;
import com.server.server.domain.ingredient.entity.Ingredient;
import com.server.server.domain.ingredient.repository.IngredientRepository;
import com.server.server.global.exception.BusinessLogicException;
import com.server.server.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @Transactional
    public Ingredient createIngredient(Ingredient ingredient){
        Ingredient saveIngredient = ingredientRepository.save(ingredient);

        return saveIngredient;
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
    @Transactional(readOnly = true) //트랜잭션 범위는 유지 /  기능을 조회함으로써 조회속도 개선
    public List<IngredientResponseDto> findIngredients(){

        return  ingredientRepository.findAll().stream()
                .map(IngredientResponseDto::new)
                .collect(Collectors.toList());


    }
}
