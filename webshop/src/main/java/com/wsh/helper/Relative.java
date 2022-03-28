package com.wsh.helper;

import com.wsh.model.Category;
import com.wsh.model.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
public class Relative {

	public static Set<Relative> makeChildrenItem(Set<Item> items) {
		Set<Relative> set = new LinkedHashSet<>();
		for (Item it : items)
			set.add(new Relative(it.getId(), it.getName()));
		return set;

	}

	public static Set<Relative> makeChildrenCategory(Set<Category> items) {
		Set<Relative> set = new LinkedHashSet<>();
		for (Category it : items)
			set.add(new Relative(it.getId(), it.getName()));
		return set;

	}
	@Getter
	private long id;
	
	@Getter
	private String name;
	 

}