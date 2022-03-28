package com.wsh.repo;

import com.wsh.model.ItemProperty;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemPropertyRepository extends CrudRepository<ItemProperty, Long>, JpaSpecificationExecutor<ItemProperty> {


    void deleteById(long id);

    @Override
    List<ItemProperty> findAll();

    ItemProperty findById(long id);

    ItemProperty findByName(String name);

    ItemProperty save(ItemProperty itemProperty);

}