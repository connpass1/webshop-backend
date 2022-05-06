package com.wsh.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wsh.helper.LogListener;
import lombok.*;
import org.hibernate.Hibernate;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

import static com.wsh.helper.Comparator.categoryComparator;

@Table(name = "Category")
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EntityListeners(LogListener.class)
@RequiredArgsConstructor
public class Category implements Serializable {

    @Setter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "cat_id" )
    private final List<Category> childrenCategory = new LinkedList();

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "parent")
    private final List<Item> items = new LinkedList<>();
    @Setter(AccessLevel.NONE)

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "parent")

    @JsonManagedReference
    private Category parent;
    @Column(length = 50)

    private String title;
    @Column(length = 25, unique = true)
    private String name;

    private String icon;
    private Integer position;

    @JsonProperty("parent")
    public String parent() {
        if (parent == null) return null;
        if (parent.parent() == null) return "$Меню@1@home";
        return parent.parent() + "$" + parent.name + '@' + parent.id + '@' + parent.icon;
    }

    @Transient
    public Category addChild(Category child) {
        if(child.parent!=null){
            child.parent.getChildrenCategory().remove(child);

        }
        childrenCategory.add(child);
        Collections.sort(childrenCategory,categoryComparator );
        child.parent = this;
        return this;
    }

    @Transient
    public Category addChild(Item child) {
        child.setParent(this);
        items.add(child);
        Collections.sort(childrenCategory,categoryComparator );
        return this;
    }

    @Transient
    public Category removeChild(Item child) {
        items.remove(child);
        child.setParent(null);
        return this;
    }

    @Transient
    public Category removeChild(Category child) {
        childrenCategory.remove(child);
        child.parent = null;
        return this;
    }
    @PrePersist
    public void prePersist() {
        if (position == null) position = 0;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Category category = (Category) o;
        return id != null && Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                '}';
    }
}