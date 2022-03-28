package com.wsh.repo;

import com.wsh.model.Profile;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProfileRepository extends CrudRepository<Profile, Long>, JpaSpecificationExecutor<Profile> {


    void deleteById(long id);

    @Override
    List<Profile> findAll();

    Profile findById(long id);


    Profile save(Profile profile);

}
