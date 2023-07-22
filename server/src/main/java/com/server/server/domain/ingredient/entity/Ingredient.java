package com.server.server.domain.ingredient.entity;

import com.server.server.domain.recipe.entity.Recipe;
import com.server.server.domain.user.entity.User;
import com.server.server.global.audit.Auditable;

import javax.persistence.*;

@Entity
public class Ingredient extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ingredientId;
    @Column
    private String ingredientName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
