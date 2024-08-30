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

    private final PackageTourBidService packageTourBidService;

    @Autowired
    public PackageTourController(PackageTourService packageTourService, ModelMapper modelMapper,
                                 NumberService numberService, CityService cityService,
                                 FoodTypeService foodTypeService, TourOperatorService tourOperatorService,
                                 TransferService transferService, PackageTourBidService packageTourBidService) {
        this.packageTourService = packageTourService;
        this.modelMapper = modelMapper;
        this.numberService = numberService;
        this.cityService = cityService;
        this.foodTypeService = foodTypeService;
        this.tourOperatorService = tourOperatorService;
        this.transferService = transferService;
        this.packageTourBidService = packageTourBidService;
    }

    //BASED ENDPOINTS

    @GetMapping("/all")
    public List<PackageTourDTO> getAllPackageTours(){
        return packageTourService.findAll().stream().map(this::convertToPackageTourDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PackageTourDTO> getOnePackageTour(@PathVariable int id){
        PackageTour packageTour = packageTourService.findOne(id);
        PackageTourDTO packageTourDTO= new PackageTourDTO();
        packageTourDTO.setStartCityId(packageTour.getBeginPlace().getId());
        packageTourDTO.setEndCityId(packageTour.getEndPlace().getId());
        packageTourDTO.setFoodTypeId(packageTour.getFoodType().getId());
        packageTourDTO.setTransferId(packageTour.getTransfer().getId());
        packageTourDTO.setTouroperatorId(packageTour.getTourOperator().getId());
        packageTourDTO.setNumberId(packageTour.getNumber().getId());
        packageTourDTO.setDuration(packageTour.getDuration());
        packageTourDTO.setDescription(packageTour.getDescription());
        packageTourDTO.setDateStart(packageTour.getDateStart());
        packageTourDTO.setNumAdults(packageTour.getNumAdults());
        packageTourDTO.setNumChildren(packageTour.getNumChildren());
        packageTourDTO.setName(packageTour.getName());
        return ResponseEntity.ok(packageTourDTO);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createPackageTour(@RequestBody @Valid PackageTourDTO  packageTourDTO){

        City startCity = cityService.findOne(packageTourDTO.getStartCityId());
        City endCity = cityService.findOne(packageTourDTO.getEndCityId());
        TourOperator tourOperator = tourOperatorService.findOne(packageTourDTO.getTouroperatorId());
        Transfer transfer = transferService.findOne(packageTourDTO.getTransferId());
        FoodType foodType = foodTypeService.findOne(packageTourDTO.getFoodTypeId());
        Number number = numberService.findOne(packageTourDTO.getNumberId());

        PackageTour packageTour = new PackageTour();
        packageTour.setName(packageTourDTO.getName());
        packageTour.setNumAdults(packageTourDTO.getNumAdults());
        packageTour.setNumChildren(packageTourDTO.getNumChildren());
        packageTour.setDuration(packageTourDTO.getDuration());
        packageTour.setDateStart(packageTourDTO.getDateStart());
        packageTour.setTourOperator(tourOperator);
        packageTour.setTransfer(transfer);
        packageTour.setBeginPlace(startCity);
        packageTour.setEndPlace(endCity);
        packageTour.setNumber(number);
        packageTour.setFoodType(foodType);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<Void> updatePackageTour(@RequestBody @Valid PackageTourDTO packageTourDTO,
                                                            int id){
        PackageTour packageTour = new PackageTour();
        packageTour.setName(packageTourDTO.getName());
        packageTour.setDateStart(packageTourDTO.getDateStart());
        packageTour.setDuration(packageTourDTO.getDuration());
        packageTour.setNumAdults(packageTourDTO.getNumAdults());
        packageTour.setNumChildren(packageTourDTO.getNumChildren());
        packageTour.setDescription(packageTourDTO.getDescription());
        packageTour.setCostPack(packageTourDTO.getCostPack());

        Number number = numberService.findOne(packageTourDTO.getNumberId());
        City startCity = cityService.findOne(packageTourDTO.getStartCityId());
        City endCity = cityService.findOne(packageTourDTO.getEndCityId());
        TourOperator tourOperator = tourOperatorService.findOne(packageTourDTO.getTouroperatorId());
        FoodType foodType = foodTypeService.findOne(packageTourDTO.getFoodTypeId());
        Transfer transfer = transferService.findOne(packageTourDTO.getTransferId());

        packageTour.setNumber(number);
        packageTour.setBeginPlace(startCity);
        packageTour.setEndPlace(endCity);
        packageTour.setTourOperator(tourOperator);
        packageTour.setFoodType(foodType);
        packageTour.setTransfer(transfer);

        packageTourService.update(id, packageTour);
        return ResponseEntity.ok().build();
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

    @PostMapping("/createPackageTourBid")
    public void createPackageTourBid (@RequestBody CreatePackageTourBidDTO createPackageTourBidDTO) {
        packageTourBidService.createPackageTourBid(createPackageTourBidDTO.getUserId(),
                createPackageTourBidDTO.getTourId());
    }

    @GetMapping("/{userId}/tourMain")
    public ResponseEntity<List<PackageTourDTOForMainPC>> getToursForMainPC(@PathVariable int userId) {
        List<PackageTourDTOForMainPC> tours = packageTourBidService.getToursForPC(userId, 2);
        return ResponseEntity.ok(tours);
    }

    @GetMapping("/{userId}/tourHistory")
    public ResponseEntity<List<PackageTourDTOForMainPC>> getToursForHistory(@PathVariable int userId) {
        int toursCount = packageTourBidService.getBidsForUserCount(userId);
        List<PackageTourDTOForMainPC> tours = packageTourBidService.getToursForPC(userId, toursCount);
        return ResponseEntity.ok(tours);
    }

    @GetMapping("/{tourId}/historyInfo")
    public ResponseEntity<PackageTourForHistoryDTO> getTourHistoryInfo(@PathVariable int tourId) {
        PackageTourForHistoryDTO tour = packageTourService.getTourInfoForHistory(tourId);
        return ResponseEntity.ok(tour);
    }

    @GetMapping("/pc-crud")
    public ResponseEntity<List<PcCrudDTO>> getTourForCrudPC() {
        List<PcCrudDTO> tours = packageTourService.getTourForCrudPC();
        return ResponseEntity.ok(tours);
    }

    private PackageTourDTO convertToPackageTourDTO(PackageTour packageTour){
        return modelMapper.map(packageTour,PackageTourDTO.class);
    }

    private PackageTour convertToPackageTour(PackageTourDTO packageTourDTO){
        return modelMapper.map(packageTourDTO,PackageTour.class);
    }
}
