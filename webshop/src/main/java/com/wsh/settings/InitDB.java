package com.wsh.settings;

import javax.annotation.PostConstruct;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wsh.model.Category;
import com.wsh.model.User;
import com.wsh.repo.RepositoryCategory;
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
		root = new Category("root");
		repositoryCategory.save(root);
		User user = new User();
		user.setName("user");
		user.setPassword("password");
		user.setRole("ADMIN");

		userRepository.save(user);
	}
}
