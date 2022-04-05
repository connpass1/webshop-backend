package com.wsh.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Category implements Serializable {
	@Setter(AccessLevel.NONE)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;




	@Setter(AccessLevel.NONE)
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "cat_id")
	private final Set<Category> childrenCategory = new LinkedHashSet ();

	@Setter(AccessLevel.NONE)
	@ManyToOne
	@JoinColumn(name = "parent")

	@JsonManagedReference
	private Category parent;
	@Column(length = 50)



	private String title;
	@Column(length = 25, unique = true)
	private String name;
	private Long number;
	private String icon;

	@Setter(AccessLevel.NONE)
	@OneToMany(mappedBy = "parent")
	private final List<Item> items = new LinkedList<>() ;


	@JsonProperty("parent")
	public String parent() {
		if (parent == null) return null;
		if (parent.name.contains("root")) return null;
		if ( parent.parent() == null) return  parent.name+'@'+parent.id+'@'+parent.icon;
		return parent.parent()+"$" +parent.name+'@'+parent.id+'@'+parent.icon;
	}

	@Transient
	public Category addChild(Category child) {
		childrenCategory.add(child);
		child.parent = this;
		return this;
	}
	@Transient
	public Category addChild(Item child) {
		child.setParent(this);
		  items .add(child);

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
	@PostLoad
	private void PreUpdate() {
		if (number == null)
		number = id;
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
		return getClass().getSimpleName() + "(" +
				"id = " + id + ")";
	}
}