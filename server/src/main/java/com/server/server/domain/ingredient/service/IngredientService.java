package com.server.server.domain.ingredient.service;

import com.server.server.domain.ingredient.entity.Ingredient;
import com.server.server.domain.ingredient.repository.IngredientRepository;
import com.server.server.global.exception.BusinessLogicException;
import com.server.server.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    public Ingredient createIngredient(Ingredient ingredient){
        Ingredient saveIngredient = ingredientRepository.save(ingredient);

        return saveIngredient;
    }
    public void deleteIngredient(long ingredientId) {
        ingredientRepository.delete(findIngredient(ingredientId));
    }

    public Ingredient findIngredient(long ingredientId) {
        return findVerifiedIngredient(ingredientId);
    }
    public Ingredient findVerifiedIngredient(long ingredientId){
        Optional<Ingredient> ingredient = ingredientRepository.findById(ingredientId);
        return  ingredient.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.RECIPE_NOT_FOUND));
    }
    public List<Ingredient> findIngredients(int page, int size, String sortBy){

        return  ingredientRepository.findAll(page,size,sortBy);
    }
}
