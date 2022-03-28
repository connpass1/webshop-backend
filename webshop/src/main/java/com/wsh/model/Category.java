package com.wsh.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wsh.helper.Relative;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Category {
	private String icon;
	@Setter(AccessLevel.NONE)
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "cat_id")
	@JsonManagedReference
	private final Set<Category> childrenCategory = new LinkedHashSet<Category>();
	@Setter(AccessLevel.NONE)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Setter(AccessLevel.NONE)
	@ManyToOne
	@JoinColumn(name = "parent")
	private Category parent;
	@Column(length = 50)
	private String title;
	@Column(length = 25, unique = true)
	private String name;
	private Long number;

	@Transient
	public Category changeParent(Category parent) {
		this.parent = parent;
		parent.childrenCategory.add(this);
		return this;
	}

	@JsonProperty("parent")
	public Relative parent() {
		if (parent == null)
			return null;
		return new Relative(parent.getId(), parent.getName());
	}


	@Transient
	public Category addChild(Category child) {
		childrenCategory.add(child);
		return this;
	}


	@JsonProperty("childrenCategory")
	public Set<Relative> children() {
		return Relative.makeChildrenCategory(childrenCategory);
	}

	@Transient
	public Category removeCategory(Category child) {
		childrenCategory.remove(child);
		return this;
	}

	@PostLoad
	private void PreUpdate() {
		if (number == null) ;
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
}