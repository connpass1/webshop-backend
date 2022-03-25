package com.wsh.repo;


	import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wsh.model.User;



	@Repository
	public interface RepositoryUser extends JpaRepository<User, Long> {
	    User findById(long id);
	    User findByName(String name);
		@Override
		@SuppressWarnings("unchecked")
		User save( User user);


	}

