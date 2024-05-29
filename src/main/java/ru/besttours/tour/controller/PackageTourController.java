package ru.besttours.tour.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.besttours.tour.dto.*;
import ru.besttours.tour.models.*;
import ru.besttours.tour.models.Number;
import ru.besttours.tour.services.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/packagetours")
public class PackageTourController {

    private final PackageTourService packageTourService;
    private final ModelMapper modelMapper;
    private final NumberService numberService;
    private final CityService cityService;
    private final FoodTypeService foodTypeService;
    private final TourOperatorService tourOperatorService;
    private final TransferService transferService;

    @Autowired
    public PackageTourController(PackageTourService packageTourService, ModelMapper modelMapper,
                                 NumberService numberService, CityService cityService,
                                 FoodTypeService foodTypeService, TourOperatorService tourOperatorService,
                                 TransferService transferService) {
        this.packageTourService = packageTourService;
        this.modelMapper = modelMapper;
        this.numberService = numberService;
        this.cityService = cityService;
        this.foodTypeService = foodTypeService;
        this.tourOperatorService = tourOperatorService;
        this.transferService = transferService;
    }

    //BASED ENDPOINTS

    @GetMapping("/all")
    public List<PackageTourDTO> getAllPackageTours(){
        return packageTourService.findAll().stream().map(this::convertToPackageTourDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PackageTourDTO getOnePackageTour(@PathVariable int id){
        return convertToPackageTourDTO(packageTourService.findOne(id));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createPackageTour(@RequestBody @Valid PackageTourDTO  packageTourDTO,
                                                        int numberId, int beginCityId, int endCityId,
                                                        int foodTypeId, int tourOperatorId,
                                                        int transferId){
        //TODO обработка всех исключений как в динамическом туре
        PackageTour packageTour = convertToPackageTour(packageTourDTO);
        Number number = numberService.findOne(numberId);
        City beginCity = cityService.findOne(beginCityId);
        City endCity = cityService.findOne(endCityId);
        FoodType foodType = foodTypeService.findOne(foodTypeId);
        TourOperator tourOperator = tourOperatorService.findOne(tourOperatorId);
        Transfer transfer = transferService.findOne(transferId);
        packageTour.setNumber(number);
        packageTour.setBeginPlace(beginCity);
        packageTour.setEndPlace(endCity);
        packageTour.setFoodType(foodType);
        packageTour.setTourOperator(tourOperator);
        packageTour.setTransfer(transfer);
        packageTour = packageTourService.create(packageTour);
        if (packageTour == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<PackageTourDTO> updatePackageTour(@RequestBody @Valid PackageTourDTO packageTourDTO,
                                                            int id){
        PackageTour packageTour = packageTourService.update(id, convertToPackageTour(packageTourDTO));
        if(packageTour == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(convertToPackageTourDTO(packageTour));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackageTour(@PathVariable int id){
        packageTourService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //OTHER ENDPOINTS

    @GetMapping("/find-name/{namePackageTour}")
    public PackageTourDTO getPackageTourByName(@PathVariable String namePackageTour){
        //TODO обработка исключений
        return convertToPackageTourDTO(packageTourService.findByName(namePackageTour));
    }

    @GetMapping("/{cityName}/actual")
    public ResponseEntity<PackageTourForCountryDTO> getActualTourForCity (@PathVariable String cityName) {
        PackageTourForCountryDTO actualTour = packageTourService.getActualTourForCity(cityName);
        return ResponseEntity.ok(actualTour);
    }

    @PostMapping("/search")
    ResponseEntity<List<PackageTourForModalDTO>> getToursForModal(@RequestBody SearchTourDTO searchTourDTO) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(searchTourDTO.getStartDate());
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        Date endDate = calendar.getTime();
        List<PackageTourForModalDTO> tours = packageTourService.packageToursForModal(
                searchTourDTO.getFromId(),
                searchTourDTO.getToId(),
                searchTourDTO.getStartDate(),
                endDate,
                searchTourDTO.getDays(),
                searchTourDTO.getAdults(),
                searchTourDTO.getChildren()
        );
        return ResponseEntity.ok(tours);
    }

    @GetMapping("/{tourId}/info")
    public ResponseEntity<PackageTourForTourPageDTO> getPackageTourInfo (@PathVariable int tourId) {
        PackageTourForTourPageDTO tourInfo = packageTourService.getTourInfo(tourId);
        return ResponseEntity.ok(tourInfo);
    }

    private PackageTourDTO convertToPackageTourDTO(PackageTour packageTour){
        return modelMapper.map(packageTour,PackageTourDTO.class);
    }

    private PackageTour convertToPackageTour(PackageTourDTO packageTourDTO){
        return modelMapper.map(packageTourDTO,PackageTour.class);
    }
}
