package com.wsh.repo;


import com.wsh.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findById(long id);


    boolean existsByNameEquals(@NonNull String name);

    User findByNameEquals(@NonNull String name);


}

