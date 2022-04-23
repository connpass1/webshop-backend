package com.wsh.repo;

import com.wsh.model.Category;
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
    @Query("select c from Category c where c.parent.id = ?1 order by c.parent.position")
    List<Category> findByParent_IdEqualsOrderByParent_PositionAsc(@NonNull Long id);

    void deleteById(long id);

    Page<Category> findByIdIsNotNullOrderByParent_Parent_IdAsc(Pageable pageable);

    @Query("select c from Category c where c.parent.id is null")
    Optional<Category> findRoot();

    //List<Category> findAll();

    Category findById(long id);

    Category findFirstByName(String name);

    Optional<Category> findFirstByNameEquals(@NonNull String name);

    List<Category> findByParent_NameIsNotIgnoreCaseAllIgnoreCaseOrderByParent_NameAsc(@Nullable String name);


}
