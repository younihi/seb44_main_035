package com.server.server.domain.ingredient.repository;

import com.server.server.domain.ingredient.entity.Ingredient;
import com.server.server.domain.recipe.entity.Recipe;
import com.server.server.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Optional<Ingredient> findByIngredientName(String ingredientName);
    Page<Ingredient> findAllByUser(User user, Pageable pageable);
}
