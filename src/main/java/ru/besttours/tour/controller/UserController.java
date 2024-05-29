package ru.besttours.tour.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.besttours.tour.dto.UserDTO;
import ru.besttours.tour.models.User;
import ru.besttours.tour.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    //BASED ENDPOINTS DEV ONLY

    @GetMapping("/all")
    public List<UserDTO> getAllUsers(){
        return userService.findAll().stream().map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable int id){
        return convertToUserDTO(userService.findOne(id));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createUser(@RequestBody @Valid UserDTO userDTO){
        User user = userService.create(convertToUser(userDTO));
        if (user == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<UserDTO> updateUser(@RequestBody @Valid UserDTO userDTO, int id){
        User user = userService.update(id, convertToUser(userDTO));
        if(user == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(convertToUserDTO(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //OTHER ENDPOINTS

    @GetMapping("/find/{lastname}/{firstname}")
    public UserDTO getUserByLastnameAndFirstname(@PathVariable String lastname, @PathVariable String firstname){
        User user = userService.findByLastnameAndFirstname(lastname, firstname);
        //TODO исключение если такого нет
        return convertToUserDTO(user);
    }

    @GetMapping("/find-by-email/{email}")
    public UserDTO getUserByEmail(@PathVariable String email){
        return convertToUserDTO(userService.findByEmail(email));
    }

    private UserDTO convertToUserDTO(User user){
        return modelMapper.map(user, UserDTO.class);
    }

    private User convertToUser(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }
}
