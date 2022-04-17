package com.wsh.model;


import com.wsh.helper.LogListener;
import com.wsh.model.ifaces.Nav;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
@Builder
@EntityListeners(LogListener.class)
public class Article implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    Long id;
    @Column(length = 25, unique = true, nullable = false)
    private String name;
    @Column(length = 10)
    private String icon;
    private Integer position;
    @Column(length = 50, unique = true, nullable = false)
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated
    @Column(name = "nav", nullable = false)
    private Nav nav=Nav.OTHER;

    @PrePersist
    public void prePersist() {
        if (position == null) position = 0;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                '}';
    }
}