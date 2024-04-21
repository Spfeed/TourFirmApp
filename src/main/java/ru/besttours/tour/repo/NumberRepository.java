package ru.besttours.tour.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.besttours.tour.models.Number;

@Repository
public interface NumberRepository extends JpaRepository<Number, Integer> {
}
