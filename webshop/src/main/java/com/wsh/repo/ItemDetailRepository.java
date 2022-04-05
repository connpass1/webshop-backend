package com.wsh.repo;

import com.wsh.model.ItemDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface ItemDetailRepository extends JpaRepository<ItemDetail, Long> {

    ItemDetail findById(long id);
    List<ItemDetail> findByIdGreaterThanEqual(@NonNull Long id);


}