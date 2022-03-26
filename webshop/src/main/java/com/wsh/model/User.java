package com.wsh.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table(name = "userok")
@JsonInclude(  JsonInclude.Include.NON_EMPTY)
public class User {
	@Setter
	@Getter
	private String adress;
	@Getter
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "order_id")
	private Set<CartOrder> cartOrder = new HashSet<>();
	@Setter
	@Getter
	@Column(unique = true)
	private String email;

	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	@Getter
	@Column(unique = true)
	private String name;
	@Setter
	@Getter
	private String password;
	@Setter
	@Getter
	private Long phone;
	@Setter
	@Getter
	private String role;

	public User(String name, String password) {
		super();
		this.name = name;
		this.password = password;
		this.role = "USER";
	}

	 

}