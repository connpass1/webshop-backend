package com.wsh.repo;

import com.wsh.model.Article;
import com.wsh.model.ifaces.Slug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Article findById(long id);
    @Query("select  a.id as id , a.name as name  , a.icon as icon" +
            " from Article a order by  a.position ")
    List<Slug> findShortList();

     void deleteByIdEquals(@NonNull Long id);

    boolean existsByIdEquals(@NonNull Long id);

}








