package ru.besttours.tour.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.besttours.tour.dto.HotelDTO;
import ru.besttours.tour.models.City;
import ru.besttours.tour.models.Hotel;
import ru.besttours.tour.services.CityService;
import ru.besttours.tour.services.HotelService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    private final HotelService hotelService;
    private final CityService cityService;
    private final ModelMapper modelMapper;

    @Autowired
    public HotelController(HotelService hotelService, CityService cityService, ModelMapper modelMapper) {
        this.hotelService = hotelService;
        this.cityService = cityService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public List<HotelDTO> getAllHotels(){
        return hotelService.findAll().stream().map(this::convertToHotelDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public HotelDTO getOneHotel(@PathVariable int id){
        return convertToHotelDTO(hotelService.findOne(id));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createHotel(@RequestBody @Valid HotelDTO hotelDTO, String cityName){
        City city = cityService.findByName(cityName);
        if(cityName == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Hotel hotel = convertToHotel(hotelDTO);
        hotel.setCity(city);
        hotel = hotelService.create(hotel);
        if(hotel == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<HotelDTO> updateHotel(@RequestBody @Valid HotelDTO hotelDTO, int id){
        Hotel updatedHotel = hotelService.update(id, convertToHotel(hotelDTO));
        if (updatedHotel == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(convertToHotelDTO(updatedHotel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable int id){
        hotelService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private HotelDTO convertToHotelDTO(Hotel hotel){
        return modelMapper.map(hotel, HotelDTO.class);
    }

    private Hotel convertToHotel(HotelDTO hotelDTO){
        return modelMapper.map(hotelDTO, Hotel.class);
    }
}
