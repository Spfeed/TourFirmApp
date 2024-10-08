package ru.besttours.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.besttours.tour.dto.UserDTO;
import ru.besttours.tour.dto.UserDTOForGlobalState;
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

    public UserDTOForGlobalState signUp(UserDTO userDTO) {
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

        UserDTOForGlobalState dto = new UserDTOForGlobalState();
        dto.setId(user.getId());
        dto.setAccessLevel(user.getAccessLevel().getName());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }


    public UserDTOForGlobalState signIn(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
        UserDTOForGlobalState dto = new UserDTOForGlobalState();
        if (passwordEncoder.matches(password, user.getPassword())) {
            dto.setId(user.getId());
            dto.setAccessLevel(user.getAccessLevel().getName());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            return dto;
        }
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
