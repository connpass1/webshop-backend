package com.wsh.repo;
import com.wsh.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Set;
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    void deleteById(long id);

    Item findById(long id);

    Item findFirstByName(String name);

    Item save(Item item);

    List<Item> findByIdIn(@NonNull Set<Long> ids);

}
