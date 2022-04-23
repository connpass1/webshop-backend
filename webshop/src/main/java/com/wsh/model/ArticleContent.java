package com.wsh.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.wsh.helper.LogListener;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
@Builder
@EntityListeners(LogListener.class)
public class ArticleContent {
    @Setter(AccessLevel.NONE)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(length = 50, unique = true, nullable = false)
    private String title;

    @OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;


    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "content = " + content + ", " +
                "title = " + title + ", " +
                "article = " + article.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ArticleContent that = (ArticleContent) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 1416403215;
    }
}
