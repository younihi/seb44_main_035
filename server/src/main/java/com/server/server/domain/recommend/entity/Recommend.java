package com.server.server.domain.recommend.entity;

import com.server.server.domain.member.entity.Member;
import com.server.server.domain.recipe.entity.Recipe;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Recommend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long recommendId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    public Recommend(Member member, Recipe recipe) {
        this.member = member;
        this.recipe = recipe;
    }
}
