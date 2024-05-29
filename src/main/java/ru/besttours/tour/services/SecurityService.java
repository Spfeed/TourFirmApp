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
        // Создание нового объекта User и установка значений напрямую
        User user = new User();
        user.setName(userDTO.getName());
        user.setFatherName(userDTO.getFatherName() != null ? userDTO.getFatherName() : "");
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());

        // Установка access level и пароля
        user.setAccessLevel(accessLevelService.findOne(1));
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Сохранение пользователя в репозиторий
        user = userRepository.save(user);

        // Возвращение ID сохраненного пользователя
        return user.getId();
    }


    public Integer signIn(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (passwordEncoder.matches(user.getPassword(), password))
            return user.getId();
        else
            throw new IllegalArgumentException("Not valid data");
    }

}
