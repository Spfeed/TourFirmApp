package ru.besttours.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.besttours.tour.dto.UserDTO;
import ru.besttours.tour.models.User;
import ru.besttours.tour.repo.UserRepository;

@Service
public class SecurityService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessLevelService accessLevelService;

    @Autowired
    public SecurityService(UserRepository userRepository, PasswordEncoder passwordEncoder, AccessLevelService accessLevelService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.accessLevelService = accessLevelService;
    }

//    @Transactional
//    public void register(User user){
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setAccessLevel(accessLevelService.findOne(1));
//        userRepository.save(user);
//    }

    public Integer signUp(UserDTO userDTO) {
        User user = User.builder()
                .name(userDTO.getName())
                .fatherName(userDTO.getFatherName() != null ? userDTO.getFatherName() : "")
                .phoneNumber(userDTO.getPhoneNumber())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .build();
        user.setAccessLevel(accessLevelService.findOne(1));
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user = userRepository.save(user);
        return user.getId();
    }

    public Integer signIn(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (passwordEncoder.matches(password, user.getPassword()))
            return user.getId();
        else
            throw new IllegalArgumentException("Not valid data");
    }

    //Нельзя использовать потому что всегда нужно передавать id пользака(чтобы узнать его роль)
    public boolean checkRole(Integer id, Integer accessLevel) throws IllegalAccessException {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (user.getAccessLevel().getId() >= accessLevel) {
            return true;
        }
        else
            throw new IllegalArgumentException
                    ("Unauthorized access");
    }
}
