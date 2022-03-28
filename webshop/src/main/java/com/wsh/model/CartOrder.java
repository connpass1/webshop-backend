package com.wsh.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.beans.Transient;
import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@AllArgsConstructor
@Builder
public class CartOrder {
    @Setter(AccessLevel.NONE)
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    @Setter(AccessLevel.NONE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "item_order_id")
	private Set<Item> items = new LinkedHashSet<>();

    @Setter(AccessLevel.NONE)
	private long lastUpdate = System.currentTimeMillis();

	private int status = 0;

	@Transient
    public CartOrder addItem(Item item) {
        items.add(item);
        return this;
    }

    @Transient
    public CartOrder removeItem(Item item) {
        items.remove(item);
        return this;
    }

    @PrePersist
    @PreUpdate
    private void time() {
        lastUpdate = System.currentTimeMillis();
    }

}
