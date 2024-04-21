package ru.besttours.tour.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.besttours.tour.models.DynamicTour;

@Repository
public interface DynamicTourRepository extends JpaRepository<DynamicTour, Integer> {
}
