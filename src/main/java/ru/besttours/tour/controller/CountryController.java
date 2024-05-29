package ru.besttours.tour.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.besttours.tour.dto.*;
import ru.besttours.tour.models.City;
import ru.besttours.tour.models.Country;
import ru.besttours.tour.services.CountryService;
import ru.besttours.tour.services.PackageTourService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryService countryService;
    private final ModelMapper modelMapper;

    private final PackageTourService packageTourService;

    @Autowired
    public CountryController(CountryService countryService, ModelMapper modelMapper, PackageTourService packageTourService) {
        this.countryService = countryService;
        this.modelMapper = modelMapper;
        this.packageTourService = packageTourService;
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

    @GetMapping("/names")
    public List<String> getCountriesNames() {
        return countryService.findAllNames();
    }

    @GetMapping("/{countryName}/info")
    public  ResponseEntity<CountryForCountryPageDTO> getCountryInfo (@PathVariable String countryName) {
        CountryForCountryPageDTO countryForCountryPageDTO = countryService.getCountryInfo(countryName);
        return ResponseEntity.ok(countryForCountryPageDTO);
    }

    @GetMapping("/{countryName}/tours")
    public ResponseEntity<List<PackageTourForCountryDTO>> getToursByCountryName(@PathVariable String countryName) {
        List<PackageTourForCountryDTO> tours = packageTourService.getToursByCountry(countryName);
        return ResponseEntity.ok(tours);
    }

    @GetMapping("/search-form")
    public ResponseEntity<List<CountryForSearchFormDTO>> getCountriesForSearchForm() {
        List<CountryForSearchFormDTO> countries = countryService.getCountriesForSearchForm();
        return ResponseEntity.ok(countries);
    }

    @GetMapping("/{countryId}/cities-for-modal")
    public ResponseEntity<List<CountryForModalDTO>> getCitiesForModal(@PathVariable int countryId) {
        List<CountryForModalDTO> cities = countryService.getCitiesForModal(countryId);
        return ResponseEntity.ok(cities);
    }

    private CountryDTO convertToCountryDTO(Country country){
        return modelMapper.map(country, CountryDTO.class);
    }

    private Country convertToCountry(CountryDTO countryDTO){
        return modelMapper.map(countryDTO, Country.class);
    }
}
