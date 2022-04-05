package com.wsh.repo;

import com.wsh.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Set;

public interface ItemRepository extends CrudRepository<Item, Long>, JpaSpecificationExecutor<Item> {
    void deleteById(long id);

    Item findById(long id);

    Item findFirstByName(String name);

    Item save(Item item);

    List<Item> findByIdIn(@NonNull Set<Long> ids);



}
