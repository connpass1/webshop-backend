package com.wsh.repo;

import com.wsh.model.Article;
import com.wsh.model.ArticleIFace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Article findById(long id);

    Article findByNameEqualsIgnoreCase(@NonNull String name);

    @Query("select  a.id   AS articleId, a.name AS articleName, a.menu AS articleMenu, a.icon AS articleIcon " +
            " from Article a order by a.menu, a.position ")
    List<ArticleIFace> findShortList();

}








