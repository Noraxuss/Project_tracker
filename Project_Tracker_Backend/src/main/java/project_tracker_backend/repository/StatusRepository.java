package project_tracker_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project_tracker_backend.domain.Status;
import project_tracker_backend.domain.StatusPurpose;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    boolean existsByName(String name);

    @Query("SELECT s FROM Status s WHERE s.name = :name")
    Optional<Status> getStatusByName(String name);

    @Query("SELECT s FROM Status s WHERE s.statusPurpose = :purpose")
    List<Status> findAllByStatusPurpose(@Param("purpose") StatusPurpose purpose);

    @Modifying
    @Query("DELETE Status s WHERE s.id = :id")
    void deleteStatusById(long id);
}
