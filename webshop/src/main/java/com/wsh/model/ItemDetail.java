package com.wsh.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.wsh.helper.LogListener;
import com.wsh.model.ifaces.Quantity;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "itemDetail_seq")
    Long id;
    private int amount = 0;
    @Column(length = 50)
    private String caption;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;




    @OneToOne(mappedBy = "itemDetail", cascade = CascadeType.ALL, optional = false, orphanRemoval = true,fetch = FetchType.LAZY)
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


    @PrePersist

    public void prePersist() {
    if (item!=null)id=item.getId();
    }
}
