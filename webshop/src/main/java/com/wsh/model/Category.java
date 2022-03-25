package com.wsh.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import helper.Relative;
import lombok.Getter;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Entity
public class Category extends AbstractItem {

	private static final long serialVersionUID = -3261056483276418061L;

	@Getter
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "items_id")
	@JsonManagedReference
	private Set<Item> items = new HashSet<>();



	public Category(String name ) {
		super(name);

	}


	public Category(String name, Category root) {
		super(name);
		super.setParent(root);
	}

	@Transient
	public Category addItem(Item item) {
		items.add(item);
		return this;}


	@JsonProperty("children")
	public Set<Relative> children(){
		return Relative.makeChildren(items); }

}