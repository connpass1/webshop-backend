package com.wsh.repo;

 
	import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wsh.model.CartOrder;
import com.wsh.model.User;

 

	@Repository
	public interface RepositoryCartOrder extends JpaRepository<CartOrder, Long> {
	 
		CartOrder findById(long id);
		@SuppressWarnings("unchecked")
		CartOrder save( CartOrder cartOrder);
		
		
	}
 
