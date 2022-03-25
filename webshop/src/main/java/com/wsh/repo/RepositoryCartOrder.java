package com.wsh.repo;


	import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wsh.model.CartOrder;



	@Repository
	public interface RepositoryCartOrder extends JpaRepository<CartOrder, Long> {

		CartOrder findById(long id);
		@Override
		@SuppressWarnings("unchecked")
		CartOrder save( CartOrder cartOrder);


	}

