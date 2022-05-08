package com.wsh.repo;
import com.wsh.model.ItemDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItemDetailRepository extends JpaRepository<ItemDetail, Long> {

    //ItemDetail findById(long id);

    @Query("select i from ItemDetail i where i.item.id = ?1")
     ItemDetail  findByItem_IdEquals(Long id);

    List<ItemDetail> findByIdGreaterThanEqual(@NonNull Long id);





}