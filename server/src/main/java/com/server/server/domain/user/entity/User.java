package com.server.server.domain.user.entity;

import com.server.server.domain.comment.entity.Comment;
import com.server.server.domain.ingredient.entity.Ingredient;
import com.server.server.domain.recipe.entity.Recipe;
import com.server.server.domain.recommend.entity.Recommend;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.server.server.domain.user.Role;
import lombok.Builder;

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
    @Enumerated(EnumType.STRING)
    private Role role;
    private String provider;
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

    @Builder
    public User(Long id, String name, String email, String password, Role role, String provider) {
        this.userId = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.provider = provider;
    }

    public User update(String name, String provider) {
        this.name = name;
        this.provider = provider;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

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
    public void addComment(Comment comment) {
        this.commentList.add(comment);
        comment.setUser(this);
    }
    public void setUserid(Long userId) {
        this.userId = userId;
    }
}
