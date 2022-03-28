package com.wsh.repo;

import com.wsh.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long>, JpaSpecificationExecutor<Item> {
    void deleteById(long id);

    Item findById(long id);

    Item findFirstByName(String name);

    Item save(Item item);

    Page<Item> findByCategory_IdEqualsOrderByCategory_IdAscPriceAscNameAsc(long category_id, Pageable pageable);

    Page<Item> findByIdIsNotNullOrderByCategory_Parent_IdAscPriceAsc(Pageable pageable);

}
