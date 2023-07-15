package com.server.server.domain.user.entity;

import com.server.server.domain.comment.entity.Comment;
import com.server.server.domain.ingredient.entity.Ingredient;
import com.server.server.domain.recipe.entity.Recipe;
import com.server.server.domain.recommend.entity.Recommend;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String name;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<Recipe> recipeList = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<Comment> commentList = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<Recommend> recommendList = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<Ingredient> ingredientList = new ArrayList<>();

    public void removeRecommend(Recommend recommend) {
        this.recommendList.remove(recommend);
        if (recommend.getUser() != this) {
            recommend.setUser(this);
        }
    }
    public void addRecommend(Recommend recommend) {
        this.recommendList.add(recommend);
        recommend.setUser(this);
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredientList.add(ingredient);
        ingredient.setUser(this);
    }
}
