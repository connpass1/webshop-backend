package com.wsh.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.wsh.model.Category;

public interface RepositoryCategory extends CrudRepository<Category, Long>, JpaSpecificationExecutor<Category> {

	@Override
	List<Category> findAll();

	Category findById(long id);

	Category findByName(String name);

	void deleteById(long id);
}
