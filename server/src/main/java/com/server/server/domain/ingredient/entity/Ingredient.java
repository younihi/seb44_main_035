package com.server.server.domain.ingredient.entity;

import javax.persistence.*;

@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ingredientId;
    @Column
    private String ingredientName;

}
