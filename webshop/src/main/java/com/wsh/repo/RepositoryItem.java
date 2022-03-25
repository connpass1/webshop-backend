package com.wsh.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.wsh.model.Item;

public interface RepositoryItem extends CrudRepository<Item, Long>, JpaSpecificationExecutor<Item> {

	@Override
	List<Item> findAll();

	Item findById(long id);

	Item findByName(String name);

	void deleteById(long id);


}
