package com.wsh.model;

import java.beans.Transient;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class CartOrder {
	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Getter
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "item_order_id")
	private Set<Item> items = new HashSet<>();

	@Getter
	private long lastupdate=System.currentTimeMillis();


	@Getter
	private int status=0;


	@Transient
	public CartOrder addItem(Item item) {
		lastupdate=System.currentTimeMillis();
		items.add(item);

		return this;

	}


	@Transient
	public CartOrder removeItem(Item item) {
		lastupdate=System.currentTimeMillis();
		items.remove(item);
		return this;

	}

	public void setStatus(int status) {
		this.status = status;
		lastupdate=System.currentTimeMillis();
	}

}
