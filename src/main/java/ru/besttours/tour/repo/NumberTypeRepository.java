package ru.besttours.tour.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.besttours.tour.models.NumberType;

@Repository
public interface NumberTypeRepository extends JpaRepository<NumberType, Integer> {
}
