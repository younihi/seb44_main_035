package com.server.server.domain.recipe.repository;

import com.server.server.domain.ingredient.entity.Ingredient;
import com.server.server.domain.recipe.entity.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Page<Recipe> findByRecipeNameContainingIgnoreCase(String recipeName, Pageable pageable);
}

