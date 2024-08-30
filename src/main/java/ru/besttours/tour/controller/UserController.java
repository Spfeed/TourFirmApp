package ru.besttours.tour.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.besttours.tour.dto.*;
import ru.besttours.tour.models.User;
import ru.besttours.tour.services.DynamicTourBidService;
import ru.besttours.tour.services.PackageTourBidService;
import ru.besttours.tour.services.SecurityService;
import ru.besttours.tour.services.UserService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    private final SecurityService securityService;
    private final PackageTourBidService packageTourBidService;
    private final DynamicTourBidService dynamicTourBidService;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper,
                          SecurityService securityService, PackageTourBidService packageTourBidService, DynamicTourBidService dynamicTourBidService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.securityService = securityService;
        this.packageTourBidService = packageTourBidService;
        this.dynamicTourBidService = dynamicTourBidService;
    }

    //BASED ENDPOINTS DEV ONLY

    @GetMapping("/all")
    public List<UserDTO> getAllUsers() {
        return userService.findAll().stream().map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable int id) {
        return convertToUserDTO(userService.findOne(id));
    }

    @PostMapping("/signUp")
    public ResponseEntity<UserDTOForGlobalState> signUp(@RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.ok(securityService.signUp(userDTO));
    }


    @PostMapping("/signIn")
    public ResponseEntity<UserDTOForGlobalState> signIn(@RequestParam String email, @RequestParam String password){
        return ResponseEntity.ok(securityService.signIn(email, password));
    }

    @PutMapping()
    public ResponseEntity<UserDTO> updateUser(@RequestBody @Valid UserDTO userDTO, int id) {
        User user = userService.update(id, convertToUser(userDTO));
        if (user == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(convertToUserDTO(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //OTHER ENDPOINTS

    @GetMapping("/find/{lastname}/{firstname}")
    public UserDTO getUserByLastnameAndFirstname(@PathVariable String lastname, @PathVariable String firstname) {
        User user = userService.findByLastnameAndFirstname(lastname, firstname);
        //TODO исключение если такого нет
        return convertToUserDTO(user);
    }

    @GetMapping("/find-by-email/{email}")
    public UserDTO getUserByEmail(@PathVariable String email) {
        return convertToUserDTO(userService.findByEmail(email));
    }

    @GetMapping("/{userId}/bids")
    public ResponseEntity<List<TourBidForPC>> getUserBids (@PathVariable int userId) {
        List<TourBidForPC> bids = userService.getBidsForPC(userId);
        return ResponseEntity.ok(bids);
    }

    @GetMapping("/getAllBidsToAccept")
    public ResponseEntity<List<TourBidForPC>> getAllBids () {
        List<TourBidForPC> bids = userService.getBidsForAdminToAccept();
        return ResponseEntity.ok(bids);
    }

    @GetMapping("/getAllBidsAccepted")
    public ResponseEntity<List<TourBidForPC>> getAllBidsAccepted () {
        List<TourBidForPC>  bids = userService.getBidsForAdminHistory();
        return ResponseEntity.ok(bids);
    }

    @PutMapping("/changeTourBidStatus")
    public void changeTourBidStatus (@RequestBody TourStatusChangeDTO dto) {
        if (dto.isDynamic()) {
        dynamicTourBidService.updatePackageTourStatus(dto.getTourId(), dto.getUserId(), dto.isStatus());
        } else {
            packageTourBidService.updatePackageTourStatus(dto.getTourId(), dto.getUserId(), dto.isStatus());
        }
    }

    @PostMapping("/getPcUsers")
    public ResponseEntity<List<UserDTOForPC>> getUsersForPC (@RequestBody String jsonString){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, String> requestMap = objectMapper.readValue(jsonString, Map.class);
            String status = requestMap.get("status");
            List<UserDTOForPC> users = userService.getUsersForPC(status);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping ("/changeUserStatus")
    public ResponseEntity<Void> changeUserStatus (@RequestBody String jsonString){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, String> requestMap = objectMapper.readValue(jsonString, Map.class);
            String email = requestMap.get("email");
            userService.changeUserStatus(email);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    private UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
