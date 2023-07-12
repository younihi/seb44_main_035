package com.server.server.domain.recipe.repository;

import com.server.server.domain.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByRecipeNameContainingIgnoreCase(String recipeName);
    List<Recipe> findByIngredientsInIgnoreCase(List<String> ingredients);
    List<Recipe> findAll();
}

