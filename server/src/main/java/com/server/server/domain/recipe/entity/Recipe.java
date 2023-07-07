package com.server.server.domain.recipe.entity;

import com.server.server.domain.comment.entity.Comment;
import com.server.server.domain.ingredient.entity.Ingredient;
import com.server.server.domain.member.entity.Member;
import com.server.server.domain.recommend.entity.Recommend;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
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
    @Column
    private int recommendCount = this.recommendList.size();

    @OneToMany(mappedBy = "recipe", cascade = {CascadeType.ALL})
    private List<Recommend> recommendList = new ArrayList<>();
    @OneToMany(mappedBy = "recipe", cascade = {CascadeType.ALL})
    private List<Comment> commentList = new ArrayList<>();
    @OneToMany(mappedBy = "recipe", cascade = {CascadeType.ALL})
    private List<Ingredient> ingredientList = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void removeRecommend(Recommend recommend) {
        this.recommendList.remove(recommend);
        if (recommend.getRecipe() != this) {
            recommend.setRecipe(this);
        }
        this.recommendCount = this.recommendList.size();
    }

    public void addRecommend(Recommend recommend) {
        this.recommendList.add(recommend);
        recommend.setRecipe(this);
        this.recommendCount = this.recommendList.size();
    }
}
