package ru.besttours.tour.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.besttours.tour.dto.DynamicTourDTO;
import ru.besttours.tour.models.City;
import ru.besttours.tour.models.DynamicTour;
import ru.besttours.tour.models.FoodType;
import ru.besttours.tour.models.Number;
import ru.besttours.tour.services.CityService;
import ru.besttours.tour.services.DynamicTourService;
import ru.besttours.tour.services.FoodTypeService;
import ru.besttours.tour.services.NumberService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dynamictours")
public class DynamicTourController {

    private final DynamicTourService dynamicTourService;
    private final ModelMapper modelMapper;
    private final NumberService numberService;
    private final CityService cityService;
    private final FoodTypeService foodTypeService;

    @Autowired
    public DynamicTourController(DynamicTourService dynamicTourService, ModelMapper modelMapper, NumberService numberService,
                                 CityService cityService, FoodTypeService foodTypeService) {
        this.dynamicTourService = dynamicTourService;
        this.modelMapper = modelMapper;
        this.numberService = numberService;
        this.cityService = cityService;
        this.foodTypeService = foodTypeService;
    }

    //BASED ENDPOINTS

    @GetMapping("/all")
    public List<DynamicTourDTO> getAllDynamicTours(){
        return dynamicTourService.findAll().stream().map(this::convertToDynamicTourDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public DynamicTourDTO getOneDynamicTour(@PathVariable int id){
        return convertToDynamicTourDTO(dynamicTourService.findOne(id));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createDynamicTour(@RequestBody @Valid DynamicTourDTO dynamicTourDTO,
                                                        int numberId, int beginCityId,
                                                        int endCityId, int foodTypeId){
        //TODO проверка на существование такого же тура(хоть id у всех и уникальный)
        DynamicTour dynamicTour = convertToDynamicTour(dynamicTourDTO);
        Number number = numberService.findOne(numberId);
        //TODO сделать исключения и errorResponse для всех id
        City beginCity = cityService.findOne(beginCityId);
        City endCity = cityService.findOne(endCityId);
        FoodType foodType = foodTypeService.findOne(foodTypeId);
        dynamicTour.setNumber(number);
        dynamicTour.setBeginPlace(beginCity);
        dynamicTour.setEndPlace(endCity);
        dynamicTour.setFoodType(foodType);
        dynamicTour = dynamicTourService.create(dynamicTour);
        if (dynamicTour == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<DynamicTourDTO> updateDynamicTour(@RequestBody @Valid DynamicTourDTO dynamicTourDTO,
                                                            int id){
        DynamicTour dynamicTour = dynamicTourService.update(id, convertToDynamicTour(dynamicTourDTO));
        if (dynamicTour == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(convertToDynamicTourDTO(dynamicTour));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDynamicTour(@PathVariable int id){
        dynamicTourService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //OTHER ENDPOINTS

    private DynamicTourDTO convertToDynamicTourDTO(DynamicTour dynamicTour){
        return modelMapper.map(dynamicTour, DynamicTourDTO.class);
    }

    private DynamicTour convertToDynamicTour(DynamicTourDTO dynamicTourDTO){
        return modelMapper.map(dynamicTourDTO, DynamicTour.class);
    }
}
