package com.wsh.repo;

 
	import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wsh.model.User;

 

	@Repository
	public interface RepositoryUser extends JpaRepository<User, Long> {
	    User findByName(String name);
	    User findById(long id);
		@SuppressWarnings("unchecked")
		User save( User user);
		
		
	}
 
