package com.wsh.repo;


import com.wsh.model.Order;
import com.wsh.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    OrderItem findById(long id);



}

