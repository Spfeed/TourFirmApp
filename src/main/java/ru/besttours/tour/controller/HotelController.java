package ru.besttours.tour.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.besttours.tour.dto.HotelDTO;
import ru.besttours.tour.dto.PcCrudDTO;
import ru.besttours.tour.models.City;
import ru.besttours.tour.models.Hotel;
import ru.besttours.tour.models.Photo;
import ru.besttours.tour.services.CityService;
import ru.besttours.tour.services.HotelService;
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
@RequestMapping("/hotels")
public class HotelController {

    private final HotelService hotelService;
    private final CityService cityService;
    private final ModelMapper modelMapper;

    private final PhotoService photoService;

    @Autowired
    public HotelController(HotelService hotelService, CityService cityService, ModelMapper modelMapper, PhotoService photoService) {
        this.hotelService = hotelService;
        this.cityService = cityService;
        this.modelMapper = modelMapper;
        this.photoService = photoService;
    }

    @GetMapping("/all")
    public List<HotelDTO> getAllHotels(){
        return hotelService.findAll().stream().map(this::convertToHotelDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public HotelDTO getOneHotel(@PathVariable int id){
        return convertToHotelDTO(hotelService.findOne(id));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createHotel(@ModelAttribute  @Valid HotelDTO hotelDTO) throws IOException {
        City city = cityService.findOne(hotelDTO.getCityId());
        if (city == null) {
            return ResponseEntity.badRequest().build();
        }

        Set<Photo> photoEntities = new HashSet<>();
        String relativeBasePath = "src/main/resources/static/images/hotels/";
        String hotelFolder = hotelDTO.getName().replace(" ", "_");

        Path currentWorkingDir = Paths.get("").toAbsolutePath();
        Path absoluteBasePath = currentWorkingDir.resolve(relativeBasePath).resolve(hotelFolder);

        // Создаем папку для отеля, если она не существует
        if (!Files.exists(absoluteBasePath)) {
            Files.createDirectories(absoluteBasePath);
        }

        if (hotelDTO.getPhotos() != null) {
            for (MultipartFile photo : hotelDTO.getPhotos()) {
                String photoFilename = photo.getOriginalFilename();
                String photoPath = "images/hotels/" + hotelFolder + "/" + photoFilename;
                Path absolutePhotoPath = absoluteBasePath.resolve(photoFilename);
                photo.transferTo(absolutePhotoPath.toFile());
                Photo photoEntity = new Photo(photoPath);
                photoService.create(photoEntity);
                photoEntities.add(photoEntity);
            }
        }

        Hotel hotel = new Hotel();
        hotel.setCity(city);
        hotel.setName(hotelDTO.getName());
        hotel.setRating(hotelDTO.getRating());
        hotel.setBeachLine(hotelDTO.getBeachLine());
        hotel.setInformation(hotelDTO.getInformation());
        hotel.setServices(hotelDTO.getServices());
        hotel.setPhotos(photoEntities);

        hotelService.create(hotel);

        for (Photo photoEntity : photoEntities) {
            if (photoEntity.getHotels() == null) {
                photoEntity.setHotels(new HashSet<>());
            }
            photoEntity.getHotels().add(hotel);
            photoService.update(photoEntity.getId(), photoEntity);
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<HotelDTO> updateHotel(@RequestBody @Valid HotelDTO hotelDTO, int id){
        Hotel updatedHotel = hotelService.update(id, convertToHotel(hotelDTO));
        if (updatedHotel == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(convertToHotelDTO(updatedHotel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable int id){
        hotelService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{hotelId}/photos")
    public List<String> getHotelPhotos (@PathVariable int hotelId) {
        List<String> photos = hotelService.getHotelPhotos(hotelId);
        return photos;
    }

    @GetMapping("/pc-crud")
    public ResponseEntity<List<PcCrudDTO>> getHotelsForCrudPC() {
        List<PcCrudDTO> hotels = hotelService.getHotelForCrudPC();
        return ResponseEntity.ok(hotels);
    }

    private HotelDTO convertToHotelDTO(Hotel hotel){
        return modelMapper.map(hotel, HotelDTO.class);
    }

    private Hotel convertToHotel(HotelDTO hotelDTO){
        return modelMapper.map(hotelDTO, Hotel.class);
    }
}
