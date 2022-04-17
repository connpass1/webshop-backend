package com.wsh.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.wsh.helper.LogListener;
import lombok.*;
import org.hibernate.Hibernate;
import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EntityListeners(LogListener.class)
public class ItemDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int amount = 0;
    @Column(length = 50)
    private String caption;
    private String description;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "property_id")
    private Set<ItemProperty> properties = new java.util.LinkedHashSet<>();
    private int quantity = 1000;
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "item_id")
    private Item item;

    @ElementCollection
    @Column(name = "photo")
    @CollectionTable(name = "item_detail_photo", joinColumns = @JoinColumn(name = "owner_id"))
    private Set<String> photo = new LinkedHashSet<>();


    @Override
    public String toString() {
        return "ItemDetail{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ItemDetail that = (ItemDetail) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
