package com.wsh.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wsh.helper.LogListener;
import com.wsh.model.ifaces.Measure;
import com.wsh.model.ifaces.Quantity;
import com.wsh.model.ifaces.Slug;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "itemDetail_id")
    Long id;


    @Column(name = "icon", columnDefinition = "TEXT")
    private String icon;
    @Column(length = 25)
    private String name;
    private int price = 0;
    private String caption;
    private int mass = 0;
    private Measure measure = Measure.гр;
    @Enumerated
    @Column(name = "quantity", nullable = false)
    private Quantity quantity = Quantity.UNLIMITED;


    @Getter(AccessLevel.NONE)
    @OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "itemDetail_id", nullable = false, unique = true)
    private ItemDetail itemDetail;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    private Category parent;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Item item = (Item) o;
        return id != null && Objects.equals(id, item.id);
    }
    @JsonProperty("parent")
    public Slug parent() {
        Category parent =  getParent();
        if (parent.getName().contains("root")) return null;
         return Slug.builder().name(parent.getName()).icon(parent.getIcon()).id(parent.getId()).build();

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