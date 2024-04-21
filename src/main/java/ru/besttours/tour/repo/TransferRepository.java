package ru.besttours.tour.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.besttours.tour.models.Transfer;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Integer> {
}
