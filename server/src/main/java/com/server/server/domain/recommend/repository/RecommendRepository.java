package com.server.server.domain.recommend.repository;

import com.server.server.domain.recommend.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {
    Optional<Recommend> findByUserUserIdAndRecipeRecipeId(Long userId, Long recipeId);
}
