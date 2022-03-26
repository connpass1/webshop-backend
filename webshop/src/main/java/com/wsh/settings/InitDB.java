package com.wsh.settings;

import javax.annotation.PostConstruct;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wsh.model.Category;
import com.wsh.model.Item;
import com.wsh.model.User;
import com.wsh.repo.RepositoryCategory;
import com.wsh.repo.RepositoryItem;
import com.wsh.repo.RepositoryUser;

@Component
public class InitDB {

	@Autowired
	private RepositoryCategory repositoryCategory;
	@Autowired
	private RepositoryUser userRepository;
	@Autowired
	private RepositoryItem  itemRepository;
	@Transient
	@PostConstruct
	private void postConstruct() {
		Category root = repositoryCategory.findByName("root");
		if (root != null)
			return;
		root = new Category("root",null);
		repositoryCategory.save(root); 
		 for(int i=0;i<10;i++) {
			Category level1Category  = new Category("Категория "+i,root );	
			repositoryCategory.save(level1Category );
			for(int j=0;j<10;j++) {
				Category  level2Category  = new Category("Категория level2"+i +""+j  ,level1Category );	
				repositoryCategory.save(level2Category);
				for(int k=0;k<10;k++) {
					Item l  = new Item("товар "+i+"-"+j+"-" +k ,level2Category); 
					l.setTitle("титл товара");
					l.setDescription("Описание");
					itemRepository.save(l);
					level1Category.addChildItem(l);
					repositoryCategory.save(level1Category);
				} 
			} 
			repositoryCategory.save(level1Category);
			
		}
		repositoryCategory.save(root);
		 
		
		User user = new User("user" ,"password");  
		user.setRole("ADMIN");  
		userRepository.save(user);
	}
}
