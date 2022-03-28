package com.wsh.repo;


import com.wsh.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);

    User findByName(String name);

    @Override
    @SuppressWarnings("unchecked")
    User save(User user);

}

