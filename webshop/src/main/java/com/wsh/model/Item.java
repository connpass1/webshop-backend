package com.wsh.model;

import javax.persistence.Entity;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Item extends AbstractItem {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public Item(String name) { super(name);}
	 

}