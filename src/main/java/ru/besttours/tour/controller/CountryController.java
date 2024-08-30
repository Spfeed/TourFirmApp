package ru.besttours.tour.controller;


import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import ru.besttours.tour.dto.*;
import ru.besttours.tour.models.Country;
import ru.besttours.tour.models.Photo;
import ru.besttours.tour.services.CountryService;
import ru.besttours.tour.services.PackageTourService;
import ru.besttours.tour.services.PhotoService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/countries")
public class CountryController  {

    private final CountryService countryService;
    private final ModelMapper modelMapper;
    private final PhotoService photoService;
    private final PackageTourService packageTourService;



    @Autowired
    public CountryController(CountryService countryService, ModelMapper modelMapper, PhotoService photoService, PackageTourService packageTourService) {
        this.countryService = countryService;
        this.modelMapper = modelMapper;
        this.photoService = photoService;
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
    public ResponseEntity<HttpStatus> createCountry(@ModelAttribute @Valid CountryDTO countryDTO) throws IOException {
        String relativeBasePath = "src/main/resources/static/images/countries/";
        String countryFolder = countryDTO.getName().replace(" ", "_");
        String photoFilename = countryDTO.getPhoto().getOriginalFilename();
        String photoPath = "images/countries/" + countryFolder + "/" + photoFilename;

        // Использование текущей рабочей директории для создания абсолютного пути
        Path currentWorkingDir = Paths.get("").toAbsolutePath();
        Path absoluteBasePath = currentWorkingDir.resolve(relativeBasePath);
        Path absolutePhotoPath = absoluteBasePath.resolve(countryFolder).resolve(photoFilename);

        System.out.println("Absolute base path: " + absoluteBasePath);
        System.out.println("Absolute photo path: " + absolutePhotoPath);

        Files.createDirectories(absolutePhotoPath.getParent());
        countryDTO.getPhoto().transferTo(absolutePhotoPath.toFile());

        Photo photoEntity = new Photo(photoPath);
        photoService.create(photoEntity);

        Country country = new Country();
        country.setName(countryDTO.getName());
        country.setDescription(countryDTO.getDescription());
        country.setVisa(countryDTO.isVisa());
        country.setLanguage(countryDTO.getLanguage());
        country.setCurrency(countryDTO.getCurrency());
        country.setLocalTime(countryDTO.getLocalTime());
        country.setReligion(countryDTO.getReligion());

        countryService.create(country);

        country.getPhotos().add(photoEntity);
        if (photoEntity.getCountries() == null) {
            photoEntity.setCountries(new HashSet<>());
        }
        photoEntity.getCountries().add(country);

        Country temp = countryService.findByName(country.getName());

        countryService.update(temp.getId(), country);

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
