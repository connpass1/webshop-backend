package com.wsh.repo;


import com.wsh.model.CartOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartOrderRepository extends JpaRepository<CartOrder, Long> {
    CartOrder findById(long id);


}

