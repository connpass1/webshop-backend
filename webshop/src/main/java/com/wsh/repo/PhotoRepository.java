package com.wsh.repo;

import com.wsh.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    void deleteById(long id);

    @Override
    List<Photo> findAll();

    Photo findById(long id);

}