package ru.besttours.tour.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.besttours.tour.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.lastName = :lastName AND u.name = :name")
    Optional<User> findByLastNameAndName(@Param("lastName") String lastName, @Param("name") String name);
    Optional<User> findByEmail(String email);


}
