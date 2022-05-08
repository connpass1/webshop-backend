package com.wsh.repo;
import com.wsh.model.Item;
import com.wsh.model.ItemDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Set;
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    void deleteById(long id);

    Item findById(long id);



    Item save(Item item);


    List<Item> findByIdIn(@NonNull Set<Long> ids);

    Page<Item> findByIdIsNotNullOrderByParent_IdAsc(Pageable pageable);




}
