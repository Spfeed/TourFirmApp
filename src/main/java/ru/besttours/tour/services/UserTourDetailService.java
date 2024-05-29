package ru.besttours.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.besttours.tour.models.User;
import ru.besttours.tour.repo.UserRepository;
import ru.besttours.tour.security.UserTourDetails;

import java.util.Optional;

public class UserTourDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserTourDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User>user =userRepository.findByEmail(email);

        if(user.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new UserTourDetails(user.get());
    }
}
