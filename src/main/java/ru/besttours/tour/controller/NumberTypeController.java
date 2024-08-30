package ru.besttours.tour.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.besttours.tour.dto.NumberTypeDTO;
import ru.besttours.tour.dto.PcCrudDTO;
import ru.besttours.tour.models.NumberType;
import ru.besttours.tour.services.NumberTypeService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/numberTypes")
public class NumberTypeController {

    private final NumberTypeService numberTypeService;
    private final ModelMapper modelMapper;

    @Autowired
    public NumberTypeController(NumberTypeService numberTypeService, ModelMapper modelMapper) {
        this.numberTypeService = numberTypeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public List<NumberTypeDTO> getAllNumberTypes(){
        return numberTypeService.findAll().stream().map(this::convertToNumberTypeDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public NumberTypeDTO getOneNumberType(@PathVariable int id){
        return convertToNumberTypeDTO(numberTypeService.findOne(id));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createNumberType(@RequestBody @Valid NumberTypeDTO numberTypeDTO){
        NumberType numberType = numberTypeService.create(convertToNumberType(numberTypeDTO));
        if(numberType == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<NumberTypeDTO> updateNumberType(@RequestBody @Valid NumberTypeDTO numberTypeDTO, int id){
        NumberType numberType = numberTypeService.update(id, convertToNumberType(numberTypeDTO));
        if (numberType == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(convertToNumberTypeDTO(numberType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNumberType(@PathVariable int id){
        numberTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pc-crud")
    public ResponseEntity<List<PcCrudDTO>> getNumberTypeForCrudPC() {
        List<PcCrudDTO> numberTypes = numberTypeService.getNumberTypeForCrudPC();
        return ResponseEntity.ok(numberTypes);
    }

    private NumberTypeDTO convertToNumberTypeDTO(NumberType numberType){
        return modelMapper.map(numberType, NumberTypeDTO.class);
    }

    private NumberType convertToNumberType(NumberTypeDTO numberTypeDTO){
        return modelMapper.map(numberTypeDTO, NumberType.class);
    }
}
