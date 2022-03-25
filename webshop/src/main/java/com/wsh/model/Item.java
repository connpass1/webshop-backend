package com.wsh.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

import helper.Relative;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import helper.Relative;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@JsonInclude(JsonInclude.Include.NON_NULL)

@NoArgsConstructor
@Entity
public class Item  implements Serializable  {/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	
	
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
	public  Item changeParent(Category parent) {
		 this.parent=parent;
		return this;}


	@JsonProperty("parent")
	public Relative parent() {
		if (parent == null)
			return null;
		return new Relative(parent.getId(), parent.getName()  );
	}
 	
@Getter @Setter
private   int amount=0;


@Getter @Setter
private   String description ;
@Getter @Setter
private  String  photo  ;
@Getter @Setter
private   int price=0;

@Getter @Setter
private   String property ;

public Item(String name, Category parent) {
	 
	this.name = name;
	this.parent = parent;
} 

}