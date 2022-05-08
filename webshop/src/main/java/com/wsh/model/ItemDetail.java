package com.wsh.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wsh.helper.LogListener;
import com.wsh.model.ifaces.Quantity;
import com.wsh.model.ifaces.Slug;
import lombok.*;
import org.hibernate.Hibernate;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

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
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @OneToOne(mappedBy = "itemDetail", cascade = CascadeType.ALL, optional = false, orphanRemoval = true, fetch = FetchType.LAZY)
    private Item item;

    public void setItem(Item item) {
        this.item = item;
        item.setItemDetail(this);
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "photo", columnDefinition = "TEXT")
    @CollectionTable(name = "item_detail_photo", joinColumns = @JoinColumn(name = "owner_photo_id"))
    private List<String> photo;

    @ElementCollection(fetch = FetchType.LAZY)
    @Column(name = "composite")
    @CollectionTable(name = "item_detail_composite", joinColumns = @JoinColumn(name = "owner_item_id"))
    private List<String> composition;

    @Transient
    public void setComposition(String[] composite) {
        if (composition == null) composition = new LinkedList<>();
        else composition.clear();
        for (String s : composite) composition.add(s);

    }


    @Transient
    public void setPhoto(String[] photos) {
        if (photo == null) photo = new LinkedList<>();
        else photo.clear();
        for (String s : photos) photo.add(s);

    }
    @JsonProperty("parents")
    public List<Slug> parents() {
        Category parent=item.getParent();
        if (parent == null) return null;
        return parent.parent();
    }
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


    @PrePersist

    public void prePersist() {
        if (item != null) id = item.getId();
    }
}
