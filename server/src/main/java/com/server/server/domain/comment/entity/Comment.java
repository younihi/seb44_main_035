package com.server.server.domain.comment.entity;

import com.server.server.domain.user.entity.User;
import com.server.server.domain.recipe.entity.Recipe;
import com.server.server.global.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;
    @Column(nullable = false)
    private String commentContent;

//    @Column(updatable = false, nullable = false, columnDefinition = "DATETIME(0)")
//    private LocalDateTime createdAt = LocalDateTime.now();
//    @Column(nullable = false, columnDefinition = "DATETIME(0)")
//    private LocalDateTime modifiedAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

}