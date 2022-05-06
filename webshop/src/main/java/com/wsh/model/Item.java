package com.wsh.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wsh.helper.LogListener;
import com.wsh.model.ifaces.Quantity;
import lombok.*;
import org.hibernate.Hibernate;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Table(name = "Item")
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
@Entity
@Builder
@EntityListeners(LogListener.class)
@AllArgsConstructor
@RequiredArgsConstructor
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
    @OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true,fetch = FetchType.LAZY)
    @JoinColumn(name = "itemDetail_id", nullable = false, unique = true )
    private ItemDetail itemDetail;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    private Category parent;

    @Enumerated
    @Column(name = "quantity", nullable = false)
    private Quantity quantity = Quantity.UNLIMITED;

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
    @PostUpdate
    @PostPersist
    public void postPersist() {
        if (itemDetail!= null)
        itemDetail.setItem(this);
    }


}