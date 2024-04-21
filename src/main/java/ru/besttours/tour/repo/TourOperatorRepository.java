package ru.besttours.tour.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.besttours.tour.models.TourOperator;

@Repository
public interface TourOperatorRepository extends JpaRepository<TourOperator, Integer> {
}
