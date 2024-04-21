package ru.besttours.tour.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.besttours.tour.models.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Integer> {
}
