package ru.besttours.tour.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.besttours.tour.models.PackageTourUserBid;
import ru.besttours.tour.models.PackageTourUserId;

@Repository
public interface PackageTourBidRepository extends JpaRepository<PackageTourUserBid, PackageTourUserId> {
}
