package com.wsh.repo;


import com.wsh.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findById(long id);

    List<Order> findByUser_IdEquals(@NonNull long id);

}

