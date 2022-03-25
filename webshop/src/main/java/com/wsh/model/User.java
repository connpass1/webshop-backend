package com.wsh.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Entity
@Table(name="userok")
public class  User {
	@Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
	@Setter @Getter
    private String name;
	@Setter @Getter
    private String password;
	@Setter @Getter
    private String position;
	@Setter @Getter
    private String role;

}