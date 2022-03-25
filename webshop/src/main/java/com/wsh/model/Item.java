package com.wsh.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
public class Item extends AbstractItem {/**
	 *
	 */
	private static final long serialVersionUID = 1L;
public Item(String name) { super(name);}


@Getter @Setter
private  String  photo  ;
@Getter @Setter 
private   int price=0;
@Getter @Setter 
private   int amount=0;

@Getter @Setter 
private   String description ;

@Getter @Setter 
private   String proerty ;





}