package com.wsh.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.wsh.helper.LogListener;
import com.wsh.model.ifaces.Nav;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(LogListener.class)
@AllArgsConstructor
@Builder
public class Article implements Serializable   {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    Long id;
    @Column(length = 25, unique = true, nullable = false)
    private String name;
    @Column(length = 10)
    private String icon;
    private Integer position;

    @Enumerated
    @Column(name = "nav", nullable = false)
    private Nav nav=Nav.OTHER;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(length = 50, unique = true, nullable = false)
    private String title;



    @PrePersist
    public void prePersist() {
        if (position == null) position = 0;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "icon = " + icon + ", " +
                "position = " + position + ", " +
                "nav = " + nav + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Article article = (Article) o;

        return Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return 150676408;
    }
}