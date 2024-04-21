package ru.besttours.tour.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.besttours.tour.dto.TourOperatorDTO;
import ru.besttours.tour.models.TourOperator;
import ru.besttours.tour.services.TourOperatorService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/touroperators")
public class TourOperatorController {

    private final TourOperatorService tourOperatorService;
    private final ModelMapper modelMapper;

    @Autowired
    public TourOperatorController(TourOperatorService tourOperatorService, ModelMapper modelMapper) {
        this.tourOperatorService = tourOperatorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public List<TourOperatorDTO> getAllTourOperators(){
        return tourOperatorService.findAll().stream().map(this::convertToTourOperatorDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TourOperatorDTO getOneTourOperator(@PathVariable int id){
        return convertToTourOperatorDTO(tourOperatorService.findOne(id));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createTourOperator(@RequestBody @Valid TourOperatorDTO tourOperatorDTO){
        TourOperator tourOperator = tourOperatorService.create(convertToTourOperator(tourOperatorDTO));
        if (tourOperator == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<TourOperatorDTO> updateTourOperator(@RequestBody @Valid TourOperatorDTO tourOperatorDTO, int id){
        TourOperator tourOperator = tourOperatorService.update(id, convertToTourOperator(tourOperatorDTO));
        if (tourOperator == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(convertToTourOperatorDTO(tourOperator));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTourOperator(@PathVariable int id){
        tourOperatorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private TourOperatorDTO convertToTourOperatorDTO(TourOperator tourOperator){
        return modelMapper.map(tourOperator, TourOperatorDTO.class);
    }

    private TourOperator convertToTourOperator(TourOperatorDTO tourOperatorDTO){
        return modelMapper.map(tourOperatorDTO, TourOperator.class);
    }
}
