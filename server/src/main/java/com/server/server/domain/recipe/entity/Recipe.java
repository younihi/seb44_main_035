package com.server.server.domain.recipe.entity;

import com.server.server.domain.comment.entity.Comment;
import com.server.server.domain.ingredient.entity.Ingredient;
import com.server.server.domain.member.entity.Member;
import com.server.server.domain.recommend.entity.Recommend;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long recipeId;
    @Column(nullable = false)
    private String recipeName;
    @Column
    private String recipeImage;
    @Column
    private String recipeIntro;
    @Column
    private List<String> cookStepContent;
    @Column
    private List<String> cookStepImage;
    @Column
    private int views = 0;

    @OneToMany(mappedBy = "recipe", cascade = {CascadeType.ALL})
    private List<Recommend> recommends = new ArrayList<>();
    @OneToMany(mappedBy = "recipe", cascade = {CascadeType.ALL})
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "recipe", cascade = {CascadeType.ALL})
    private List<Ingredient> ingredients = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
