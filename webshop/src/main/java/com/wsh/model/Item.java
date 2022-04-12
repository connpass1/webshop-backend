package com.wsh.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wsh.helper.LogListener;
import lombok.*;
import org.hibernate.Hibernate;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
@AllArgsConstructor
@Entity
@Builder
@EntityListeners(LogListener.class)
public class Item implements Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String icon;
    @Column(length = 25)
    private String name;
    private int price = 0;


    @Getter(AccessLevel.NONE)
    @OneToOne(mappedBy = "item", orphanRemoval = true)
    private ItemDetail itemDetail;


    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    private Category parent;

    @JsonProperty("itemDetailId")
    public Long getItemDetailId() {
        if (itemDetail == null) return null;
        return itemDetail.getId();
    }

    @JsonProperty("parent")
    public String parent() {
        if (parent.getName().contains("root")) return null;
        if (parent.parent() == null) return parent.getName() + '@' + parent.getId() + '@' + parent.getIcon();
        return parent.parent() + "$" + parent.getName() + '@' + parent.getId() + '@' + parent.getIcon();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Item item = (Item) o;
        return id != null && Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                '}';
    }
}