package ru.besttours.tour.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.besttours.tour.models.FoodType;

@Repository
public interface FoodTypeRepository extends JpaRepository<FoodType, Integer> {
}
