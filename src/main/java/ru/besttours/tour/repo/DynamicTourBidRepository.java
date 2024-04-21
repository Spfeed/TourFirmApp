package ru.besttours.tour.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.besttours.tour.models.DynamicTourBid;
import ru.besttours.tour.models.DynamicTourUserId;

@Repository
public interface DynamicTourBidRepository extends JpaRepository<DynamicTourBid, DynamicTourUserId> {
}
