package ru.besttours.tour.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.besttours.tour.dto.CityDTO;
import ru.besttours.tour.models.City;
import ru.besttours.tour.models.Country;
import ru.besttours.tour.services.CityService;
import ru.besttours.tour.services.CountryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;
    private final CountryService countryService;
    private final ModelMapper modelMapper;

    @Autowired
    public CityController(CityService cityService, CountryService countryService, ModelMapper modelMapper) {
        this.cityService = cityService;
        this.countryService = countryService;
        this.modelMapper = modelMapper;
    }

    //BASIC ENDPOINTS

    @GetMapping("/all")
    public List<CityDTO> getAllCities(){
        return cityService.findAll().stream().map(this::convertToCityDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CityDTO getOneCity(@PathVariable int id){
        return convertToCityDTO(cityService.findOne(id));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createCity(@RequestBody @Valid CityDTO cityDTO, String countryName){
        //TODO норм проверка существования страны
        Country country = countryService.findByName(countryName);
        if (country == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        City city = convertToCity(cityDTO);
        city.setCountry(country);
        city = cityService.create(city);
        if(city == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<CityDTO> updateCity(@RequestBody @Valid CityDTO cityDTO, int id){
        //TODO сделать проверку существования города
        City updatedCity = cityService.update(id, convertToCity(cityDTO));
        if(updatedCity == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(convertToCityDTO(updatedCity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable int id) {
        cityService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //OTHER ENDPOINTS

    @GetMapping("/hotels/{cityName}")
    public List<String> getCityHotels(@PathVariable String cityName){
        City city = cityService.findByName(cityName);
        //TODO исключение если не найден
        return cityService.findAllHotels(city);
    }

    private CityDTO convertToCityDTO(City city){
        return modelMapper.map(city, CityDTO.class);
    }

    private City convertToCity(CityDTO cityDTO){
        return modelMapper.map(cityDTO, City.class);
    }
}