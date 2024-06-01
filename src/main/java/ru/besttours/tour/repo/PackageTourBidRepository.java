    package ru.besttours.tour.repo;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;
    import ru.besttours.tour.models.PackageTourBid;
    import ru.besttours.tour.models.PackageTourUserId;

    import java.util.List;

    @Repository
    public interface PackageTourBidRepository extends JpaRepository<PackageTourBid, PackageTourUserId> {
        List<PackageTourBid> findByIdUserId(int userId);
    }
