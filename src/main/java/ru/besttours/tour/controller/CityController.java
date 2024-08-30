package ru.besttours.tour.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.besttours.tour.dto.*;
import ru.besttours.tour.models.City;
import ru.besttours.tour.models.Country;
import ru.besttours.tour.models.Photo;
import ru.besttours.tour.services.CityService;
import ru.besttours.tour.services.CountryService;
import ru.besttours.tour.services.PackageTourService;
import ru.besttours.tour.services.PhotoService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;
    private final CountryService countryService;
    private final ModelMapper modelMapper;
    private final PhotoService photoService;
    private final PackageTourService packageTourService;

    @Autowired
    public CityController(CityService cityService, CountryService countryService, ModelMapper modelMapper, PhotoService photoService, PackageTourService packageTourService) {
        this.cityService = cityService;
        this.countryService = countryService;
        this.modelMapper = modelMapper;
        this.photoService = photoService;
        this.packageTourService = packageTourService;
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
    public ResponseEntity<HttpStatus> createCity(@ModelAttribute @Valid CityDTO cityDTO) throws IOException {
        Country country = countryService.findOne(cityDTO.getCountryId());
        if (country == null){
            return ResponseEntity.badRequest().build();
        }

        Set<Photo> photoEntities = new HashSet<>();
        String relativeBasePath = "src/main/resources/static/images/cities/";

        Path currentWorkingDir = Paths.get("").toAbsolutePath();
        Path absoluteBasePath = currentWorkingDir.resolve(relativeBasePath);

        City city = new City();
        city.setCountry(country);
        city.setName(cityDTO.getName());
        city.setDescription(cityDTO.getDescription());

        if (cityDTO.getPhotos() != null) {
            for (MultipartFile photo : cityDTO.getPhotos()) {
                String photoFilename = photo.getOriginalFilename();
                String photoPath = "images/cities/" + photoFilename;
                Path absolutePhotoPath = absoluteBasePath.resolve(photoFilename);
                Files.createDirectories(absolutePhotoPath.getParent());
                photo.transferTo(absolutePhotoPath.toFile());
                Photo photoEntity = new Photo(photoPath);
                photoService.create(photoEntity);
                photoEntities.add(photoEntity);
            }
        }

        city.setPhotos(photoEntities);
        cityService.create(city);

        for (Photo photoEntity : photoEntities) {
            if (photoEntity.getCities() == null) {
                photoEntity.setCities(new HashSet<>());
            }
            photoEntity.getCities().add(city);
            photoService.update(photoEntity.getId(), photoEntity);
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/addRussianCity")
    public ResponseEntity<HttpStatus> createRussianCity(@ModelAttribute @Valid CityDTO cityDTO) throws IOException {
        Country country = countryService.findOne(cityDTO.getCountryId());
        if (country == null){
            return ResponseEntity.badRequest().build();
        }

        Set<Photo> photoEntities = new HashSet<>();
        String cityFolderName = cityDTO.getName().replaceAll(" ", "_").toLowerCase();
        String relativeBasePath = "src/main/resources/static/images/cities/" + cityFolderName + "/";

        Path currentWorkingDir = Paths.get("").toAbsolutePath();
        Path absoluteBasePath = currentWorkingDir.resolve(relativeBasePath);

        City city = new City();
        city.setCountry(country);
        city.setName(cityDTO.getName());
        city.setDescription(cityDTO.getDescription());

        if (cityDTO.getPhotos() != null) {
            for (MultipartFile photo : cityDTO.getPhotos()) {
                String photoFilename = photo.getOriginalFilename();
                String photoPath = "images/cities/" + cityFolderName + "/" + photoFilename;
                Path absolutePhotoPath = absoluteBasePath.resolve(photoFilename);
                Files.createDirectories(absolutePhotoPath.getParent());
                photo.transferTo(absolutePhotoPath.toFile());
                Photo photoEntity = new Photo(photoPath);
                photoService.create(photoEntity);
                photoEntities.add(photoEntity);
            }
        }

        city.setPhotos(photoEntities);
        cityService.create(city);

        for (Photo photoEntity : photoEntities) {
            if (photoEntity.getCities() == null) {
                photoEntity.setCities(new HashSet<>());
            }
            photoEntity.getCities().add(city);
            photoService.update(photoEntity.getId(), photoEntity);
        }

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

    @GetMapping("/hotels/{cityId}")
    public ResponseEntity<List<HotelForDynTourCreateionDTO>> getCityHotels(@PathVariable int cityId){
        List<HotelForDynTourCreateionDTO> hotels = cityService.findAllHotels(cityId);
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/{cityName}/info")
    public ResponseEntity<CityForCityDTO> getCityInfo(@PathVariable String cityName) {
        CityForCityDTO cityInfo = cityService.getCityInfo(cityName);
        return ResponseEntity.ok(cityInfo);
    }

    @GetMapping("/{cityName}/toursOnPage")
    public ResponseEntity<List<PackageTourForCountryDTO>> getToursFromCity (@PathVariable String cityName) {
        List<PackageTourForCountryDTO> tours = packageTourService.getToursByCity(cityName);
        return ResponseEntity.ok(tours);
    }

    @GetMapping("/search-form")
    public ResponseEntity<List<CityForSearchFormDTO>> getCitiesForSearchForm() {
        List<CityForSearchFormDTO> cities = cityService.getCitiesForSearchForm();
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/pc-crud")
    public ResponseEntity<List<PcCrudDTO>> getCitiesForCrudPC() {
        List<PcCrudDTO> cities = cityService.getCitiesForCrudPc();
        return ResponseEntity.ok(cities);
    }

    private CityDTO convertToCityDTO(City city){
        return modelMapper.map(city, CityDTO.class);
    }

    private City convertToCity(CityDTO cityDTO){
        return modelMapper.map(cityDTO, City.class);
    }
}
