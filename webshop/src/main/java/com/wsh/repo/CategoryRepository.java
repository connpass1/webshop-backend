package com.wsh.repo;

import com.wsh.model.Category;
import com.wsh.model.CategoryInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from Category c where c.parent.id is null")
     Category  findRoot();

    Category findById(long id);

    Category findFirstByName(String name);

    @Query("select c from Category c where c.parent.parent is null order by c.position")
    List<CategoryInfo> findByParent_ParentIsNotNull();

}
