package ru.besttours.tour.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.besttours.tour.models.AccessLevel;

import java.util.Optional;

@Repository
public interface AccessLevelRepository extends JpaRepository<AccessLevel, Integer> {
    @Query("SELECT a FROM AccessLevel a WHERE LOWER(a.name) = LOWER(:name)")
    Optional<AccessLevel> findByName(@Param("name") String name);
}
