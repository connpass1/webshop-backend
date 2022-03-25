package com.wsh.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

import helper.Relative;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
public class AbstractItem implements Serializable {

	private static final long serialVersionUID = -3261056483276418061L;
	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Getter
	@Setter
	private String name;

	@Setter
	@ManyToOne
	@JoinColumn(name = "category")
	private Category category;

	public AbstractItem(String name) {
		this.name = name;
	}

	@JsonProperty("parent")
	public Relative parents() {
		if (category == null)
			return null;

		return new Relative(category.getId(), category.getName());
	}

}