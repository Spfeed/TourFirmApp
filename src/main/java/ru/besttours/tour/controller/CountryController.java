package ru.besttours.tour.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.besttours.tour.dto.CountryDTO;
import ru.besttours.tour.models.City;
import ru.besttours.tour.models.Country;
import ru.besttours.tour.services.CountryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryService countryService;
    private final ModelMapper modelMapper;

    @Autowired
    public CountryController(CountryService countryService, ModelMapper modelMapper) {
        this.countryService = countryService;
        this.modelMapper = modelMapper;
    }

    //BASED ENDPOINTS

    @GetMapping("/all")
    public List<CountryDTO> getAllCountries(){
        return countryService.findAll().stream().map(this::convertToCountryDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CountryDTO getOneCountry(@PathVariable int id) {
        return convertToCountryDTO(countryService.findOne(id));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createCountry(@RequestBody @Valid CountryDTO countryDTO) { //TODO bindingResult
        Country country = convertToCountry(countryDTO);
        country = countryService.create(country);

        //TODO норм проверка с исключением

        if(country == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<CountryDTO>updateCountry(@RequestBody @Valid int id, CountryDTO countryDTO){
        Country updatedCountry = countryService.update(id, convertToCountry(countryDTO));
        if(updatedCountry == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(convertToCountryDTO(updatedCountry));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable int id){
        countryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-name/{countryName}")
    public CountryDTO getCountryByName(@PathVariable String countryName){
        return convertToCountryDTO(countryService.findByName(countryName));
    }

    @GetMapping("/all-cities/{countryName}")
    public List<String> getAllCountryCities(@PathVariable String countryName){
        Country country = countryService.findByName(countryName);
        //TODO обработка исключения
        return countryService.findCities(country);
    }

    private CountryDTO convertToCountryDTO(Country country){
        return modelMapper.map(country, CountryDTO.class);
    }

    private Country convertToCountry(CountryDTO countryDTO){
        return modelMapper.map(countryDTO, Country.class);
    }
}
