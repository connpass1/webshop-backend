package com.wsh.repo;

import com.wsh.model.Article;
import com.wsh.model.ArticleInfo;
import com.wsh.model.ifaces.Nav;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {


    List<ArticleInfo> findByIdIsNotNullOrderByNavAscPositionAsc( );

    @Query("select a  from Article a where a.nav = ?1 order by a.position")
    List<ArticleInfo> findByNav(@NonNull Nav nav);



    boolean existsByIdEquals(@NonNull Long id);

}








