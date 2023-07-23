package com.server.server.domain.recipe.entity;

import com.server.server.domain.comment.entity.Comment;
import com.server.server.domain.ingredient.entity.Ingredient;
import com.server.server.domain.user.entity.User;
import com.server.server.domain.recommend.entity.Recommend;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    @ElementCollection
    @CollectionTable(name = "recipe_cook_step", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "cook_step_content")
    private List<String> cookStepContent = new ArrayList<>();
    @ElementCollection
    @CollectionTable(name = "recipe_cook_step_image", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "cook_step_image")
    private List<String> cookStepImage = new ArrayList<>();
    @Column
    private int views = 0;
    @OneToMany(mappedBy = "recipe", cascade = {CascadeType.ALL})
    private List<Recommend> recommendList = new ArrayList<>();
    @OneToMany(mappedBy = "recipe", cascade = {CascadeType.ALL})
    private List<Comment> commentList = new ArrayList<>();
    @OneToMany(mappedBy = "recipe", cascade = {CascadeType.ALL})
    private List<Ingredient> ingredients = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private int recommendCount = this.recommendList.size();

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

    public void addComment(Comment comment) {
        this.commentList.add(comment);
        comment.setRecipe(this);
    }

    public void addCookStepImage(String image) {
        this.cookStepImage.add(image);
    }

    public void removeCookStepImage() {

    }

    public void removeIngredient() {
        this.ingredients = new ArrayList<>();
    }
}
