package ru.besttours.tour.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.besttours.tour.dto.FoodTypeDTO;
import ru.besttours.tour.models.FoodType;
import ru.besttours.tour.services.FoodTypeService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/foodTypes")
public class FoodTypeController {

    private final FoodTypeService foodTypeService;
    private final ModelMapper modelMapper;

    @Autowired
    public FoodTypeController(FoodTypeService foodTypeService, ModelMapper modelMapper) {
        this.foodTypeService = foodTypeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public List<FoodTypeDTO> getAllFoodTypes() {
        return foodTypeService.findAll().stream().map(this::convertToFoodTypeDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public FoodTypeDTO getOneFoodType(@PathVariable int id) {
        return convertToFoodTypeDTO(foodTypeService.findOne(id));
    }

    @PostMapping("/add")
    public FoodTypeDTO createFoodType(@RequestBody @Valid FoodTypeDTO foodTypeDTO) {
        FoodType foodType = convertToFoodType(foodTypeDTO);
        return convertToFoodTypeDTO(foodTypeService.save(foodType));
    }

    @PutMapping()
    public ResponseEntity<FoodTypeDTO> updateFoodType(@RequestBody @Valid FoodTypeDTO foodTypeDTO, int id){
        FoodType foodType = convertToFoodType(foodTypeDTO);
        FoodType updatedFoodType = foodTypeService.update(id, foodType);
        if(updatedFoodType == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToFoodTypeDTO(updatedFoodType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoodType(@PathVariable int id) {
        foodTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private FoodTypeDTO convertToFoodTypeDTO(FoodType foodType) {
        return modelMapper.map(foodType, FoodTypeDTO.class);
    }

    private FoodType convertToFoodType(FoodTypeDTO foodTypeDTO) {
        return modelMapper.map(foodTypeDTO, FoodType.class);
    }
}
