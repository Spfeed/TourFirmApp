package ru.besttours.tour.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.besttours.tour.dto.UserDTO;
import ru.besttours.tour.dto.UserDTOForGlobalState;
import ru.besttours.tour.dto.UserLogInDTO;
import ru.besttours.tour.models.User;
import ru.besttours.tour.services.SecurityService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final SecurityService securityService;

    @Autowired
    public AuthController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/signIn")
    public ResponseEntity<UserDTOForGlobalState> signIn(@RequestBody UserLogInDTO userLogInDTO, HttpSession session) {
        UserDTOForGlobalState userData = securityService.signIn(userLogInDTO.getEmail(), userLogInDTO.getPassword());
        session.setAttribute("userData", userData);
        return ResponseEntity.ok(userData);
    }

    @PostMapping("/signUp")
    public ResponseEntity<UserDTOForGlobalState> signUp(@RequestBody @Valid UserDTO userDTO, HttpSession session) {
        UserDTOForGlobalState userData = securityService.signUp(userDTO);
        session.setAttribute("userData", userData);
        return new ResponseEntity<>(userData, HttpStatus.CREATED);
    }

    @GetMapping("/signOut")
    public void signOut(HttpSession session) {
        session.invalidate();
    }
}
