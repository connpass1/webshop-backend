package com.wsh.repo;

import com.wsh.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    void deleteById(long id);

    List<Category> findAll();

    Category findById(long id);

    Category findFirstByName(String name);

    Optional<Category> findFirstByNameEquals(@NonNull String name);

    List<Category> findByParent_NameIsNotIgnoreCaseAllIgnoreCaseOrderByParent_NameAsc(@Nullable String name);


}
