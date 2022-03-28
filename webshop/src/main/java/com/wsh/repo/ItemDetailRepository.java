package com.wsh.repo;

import com.wsh.model.ItemDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemDetailRepository extends JpaRepository<ItemDetail, Long> {

    @Override
    List<ItemDetail> findAll();

    ItemDetail findById(long id);


}