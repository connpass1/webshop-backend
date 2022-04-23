package com.wsh.repo;
import com.wsh.model.ItemDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemDetailRepository extends JpaRepository<ItemDetail, Long> {

    ItemDetail findById(long id);

    List<ItemDetail> findByIdGreaterThanEqual(@NonNull Long id);



    Page<ItemDetail> findByIdIsNotNullOrderByItem_Parent_IdAsc(Pageable pageable);




}