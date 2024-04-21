package ru.besttours.tour.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.besttours.tour.models.PackageTour;

import java.util.Optional;

@Repository
public interface PackageTourRepository extends JpaRepository<PackageTour, Integer> {
    Optional<PackageTour> findByName(String name);
}
