package com.wsh.repo;
import com.wsh.model.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    void deleteById(long id);
    Profile findById(long id);

    boolean existsByUser_IdEquals(@NonNull Long id);

    Page<Profile> findByUser_IdIsNotNullOrderByUser_NameAsc(Pageable pageable);



    @Query("select p from Profile p where p.user.id = ?1")
    Optional<Profile> findByUser_IdEquals(@NonNull Long id);
    Profile save(Profile profile);

}
