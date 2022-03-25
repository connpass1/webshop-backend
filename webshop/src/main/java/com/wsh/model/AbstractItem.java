package com.wsh.model;

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
public class AbstractItem implements Serializable {

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
	public AbstractItem(String name) {
		this.name = name;
	}

	@Transient
	public AbstractItem changeParent(Category parent) {
		 this.parent=parent;
		return this;}


	@JsonProperty("parent")
	public Relative parent() {
		if (parent == null)
			return null;
		return new Relative(parent.getId(), parent.getName());
	}

}