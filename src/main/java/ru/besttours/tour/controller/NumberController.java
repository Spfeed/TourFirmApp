package ru.besttours.tour.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.besttours.tour.dto.NumberDTO;
import ru.besttours.tour.dto.NumberToFormDTO;
import ru.besttours.tour.models.Hotel;
import ru.besttours.tour.models.Number;
import ru.besttours.tour.models.NumberType;
import ru.besttours.tour.services.HotelService;
import ru.besttours.tour.services.NumberService;
import ru.besttours.tour.services.NumberTypeService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/numbers")
public class NumberController {

    private final NumberService numberService;
    private final ModelMapper modelMapper;
    private final HotelService hotelService;
    private final NumberTypeService numberTypeService;

    @Autowired
    public NumberController(NumberService numberService, ModelMapper modelMapper, HotelService hotelService, NumberTypeService numberTypeService) {
        this.numberService = numberService;
        this.modelMapper = modelMapper;
        this.hotelService = hotelService;
        this.numberTypeService = numberTypeService;
    }

    @GetMapping("/all")
    public List<NumberDTO> getAllNumbers(){
        return numberService.findAll().stream().map(this::convertToNumberDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public NumberDTO getOneNumber(@PathVariable int id){
        return convertToNumberDTO(numberService.findOne(id));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createNumber (@RequestBody @Valid NumberDTO numberDTO, String hotelName, int numberTypeId){
        Number number = convertToNumber(numberDTO);
        Hotel hotel = hotelService.findByName(hotelName);
        NumberType numberType = numberTypeService.findOne(numberTypeId);
        if (hotel == null || numberType == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        number.setHotel(hotel);
        number.setNumberType(numberType);
        number = numberService.create(number);
        if (number == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<NumberDTO> updateNumber(@RequestBody @Valid NumberDTO numberDTO, int id){
        Number number = numberService.update(id, convertToNumber(numberDTO));
        if (number == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(convertToNumberDTO(number));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNumber(@PathVariable int id){
        numberService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{hotelId}/numbersForForm")
    public ResponseEntity<List<NumberToFormDTO>> getNumbersForFrom(@PathVariable int hotelId) {
        List<NumberToFormDTO> numbers = numberService.getNumbersForHotel(hotelId);
        return ResponseEntity.ok(numbers);
    }


    private NumberDTO convertToNumberDTO(Number number){
        return modelMapper.map(number,NumberDTO.class);
    }

    private Number convertToNumber(NumberDTO numberDTO){
        return modelMapper.map(numberDTO,Number.class);
    }
}
