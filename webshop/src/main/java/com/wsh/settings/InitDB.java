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
	@Transient
	@PostConstruct
	private void postConstruct() {
		Category root = repositoryCategory.findByName("root");
		if (root != null)
			return;
		root = new Category("root", null);

		for (int i = 0; i < 10; i++) {
			Category сat1 = new Category("Категория a " + i, root);
			for (int j = 0; j < 10; j++) {
				Category с1 = new Category("Категория b " + (i*10+j), сat1);
				for (int l = 0; l < 10; l++) {  
				Item it = new Item("Продукт №" + (i*100+j*10+l), с1);
					it.setAmount(1000);  
					it.setPrice(i*100-j*10+3*l)  ;
					it.setDescription("хорошая вещь"); 
					it.setCaption("Заголовок");
					it.setProperty("не боится морозов");;
					} 
			} 
		}
		repositoryCategory.save(root);
		User user = new User("user", "password");
		user.setRole("ADMIN");
		userRepository.save(user);
	}
}
