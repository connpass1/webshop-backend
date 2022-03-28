package com.wsh.repo;

import com.wsh.model.Category;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    void deleteById(long id);

    List<Category> findAll();

    Category findById(long id);

    Category findFirstByName(String name);

    Optional<Category> findFirstByNameEquals(@NonNull String name);

    List<Category> findByParent_NameIsNotIgnoreCaseAllIgnoreCaseOrderByParent_NameAsc(@Nullable String name);


}
