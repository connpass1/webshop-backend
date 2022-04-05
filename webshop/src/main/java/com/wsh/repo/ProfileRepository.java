package com.wsh.repo;

import com.wsh.model.Profile;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends CrudRepository<Profile, Long>, JpaSpecificationExecutor<Profile> {


    void deleteById(long id);

    @Override
    List<Profile> findAll();

    Profile findById(long id);

    @Query("select p from Profile p where p.user.id = ?1")
    Optional<Profile> findByUser_IdEquals(@NonNull Long id);


    Profile save(Profile profile);

}
