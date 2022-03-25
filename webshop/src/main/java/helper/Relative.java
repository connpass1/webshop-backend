package helper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.wsh.model.Item;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
public class Relative {
	
	@Getter 
	private long id;
	@Getter
	private String name; 
 

public static Set<Relative> makeChildren(Set<Item> items) {
	Set<Relative> set=new HashSet<Relative>();
	
	for( Item it:items ) set.add(new Relative(it.getId(),it.getName())) ;
	return set;
	
}
	
	
	
}