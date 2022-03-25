package com.wsh.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import helper.Relative;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Category implements Serializable {

	private static final long serialVersionUID = -3261056483276418061L;
	
	
	
	
	@Getter
	@Setter
	private String icon;
	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Getter
	@Setter
	@Column(unique=true)
	private String name;
	@Setter
	@ManyToOne
	@JoinColumn(name = "parent")
	private Category parent;

	@Getter
	@Setter
	private String title;
 
	@Transient
	public  Category changeParent(Category parent) {
		 this.parent=parent;
		return this;}


	@JsonProperty("parent")
	public Relative parent() {
		if (parent == null)
			return null;
		return new Relative(parent.getId(), parent.getName( ));
	}
	
	 
	
	

//	@Getter
//	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "items_id")
//	@JsonManagedReference
//	private Set<Item> childrenItems = new HashSet<>();
	
	@Getter
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cat_id")
	@JsonManagedReference
	private Set<Category > childrenCategory  = new HashSet<Category >();

	@Getter
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "itemd_id")
	@JsonManagedReference
	private Set<Item> childrenItem  = new HashSet<Item>();
	 
	@Transient
	public Category addChildItem(Item child) {
		childrenItem.add(child);
		return this;}

	@JsonProperty("childrenItem")
	public Set<Relative>childrenItem(){
		return Relative.makeChildrenItem(childrenItem); }
	


	@Transient
	public Category removeChildItem(Item  child) {
		childrenItem.remove(child);
		return this;}

 
	@Transient
	public Category addChildrenCategory(Category  child) {
		childrenCategory .add(child);
		return this;}

	@JsonProperty("childrenCategory")
	public Set<Relative> children(){
		return Relative.makeChildrenCategory(childrenCategory); }
	


	@Transient
	public Category removeCategory(Category  child) {
		childrenCategory.remove(child);
		return this;}


	public Category(String name, Category parent) {
		 
		this.name = name;
		this.parent = parent;
	}

}