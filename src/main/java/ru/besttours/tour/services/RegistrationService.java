package ru.besttours.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.besttours.tour.models.User;
import ru.besttours.tour.repo.UserRepository;

@Service
public class RegistrationService {

     private final UserRepository userRepository;
     private final PasswordEncoder passwordEncoder;
     private final AccessLevelService accessLevelService;

     @Autowired
    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AccessLevelService accessLevelService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
         this.accessLevelService = accessLevelService;
     }

    @Transactional
    public void register(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAccessLevel(accessLevelService.findOne(1));
        userRepository.save(user);
    }

}
