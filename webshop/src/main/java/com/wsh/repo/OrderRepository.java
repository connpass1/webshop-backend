package com.wsh.repo;
import com.wsh.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Set;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findById(long id);

    Page<Order> findByIdIsNotNullOrderByStatusAscLastUpdateStatusAsc(Pageable pageable);

    @Query("select o from Order o where o.profile.id = ?1 order by o.status")
    Page<Order> findByProfile_Id (@NonNull Long id, Pageable pageable);

    @Query("select o from Order o where o.profile.id = ?1 and o.status = ?2 order by o.lastUpdateStatus")
    Set<Order> findByProfileAndStatus(@NonNull Long id, @Nullable int status);



}

