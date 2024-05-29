package ru.besttours.tour.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.besttours.tour.models.City;
import ru.besttours.tour.models.PackageTour;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PackageTourRepository extends JpaRepository<PackageTour, Integer> {
    Optional<PackageTour> findByName(String name);
    List<PackageTour> findByEndPlace(City city);

    @Query("SELECT t FROM PackageTour t WHERE t.beginPlace.id = :fromId " +
    "AND t.endPlace.id = :toId " +
    "AND t.dateStart BETWEEN :startDate AND :endDate " +
    "AND t.duration = :days " +
    "AND t.numAdults = :adults " +
    "AND t.numChildren = :children")
    List<PackageTour> findToursByParameters(@Param("fromId") int fromId,
                                            @Param("toId") int toId,
                                            @Param("startDate")Date startDate,
                                            @Param("endDate") Date endDate,
                                            @Param("days") int days,
                                            @Param("adults") int adults,
                                            @Param("children") int children);
}
