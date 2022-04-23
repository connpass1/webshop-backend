package com.wsh.repo;

import com.wsh.model.ArticleContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleContentRepository extends JpaRepository<ArticleContent, Long> {
    Optional<ArticleContent> findByArticle_IdEquals(@NonNull Long id);

    Optional<ArticleContent> findByIdEquals(@NonNull Long id);




    boolean existsByIdEquals(@NonNull Long id);

}








