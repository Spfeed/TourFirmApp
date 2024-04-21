package ru.besttours.tour.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.besttours.tour.dto.PhotoDTO;
import ru.besttours.tour.models.Photo;
import ru.besttours.tour.services.PhotoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    private final PhotoService photoService;
    private final ModelMapper modelMapper;

    @Autowired
    public PhotoController(PhotoService photoService, ModelMapper modelMapper) {
        this.photoService = photoService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public List<PhotoDTO> getAllPhotos(){
        return photoService.findAll().stream().map(this::convertToPhotoDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PhotoDTO getOnePhoto(@PathVariable int id){
        return convertToPhotoDTO(photoService.findOne(id));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createPhoto(@RequestBody @Valid PhotoDTO photoDTO){
        Photo photo = photoService.create(convertToPhoto(photoDTO));
        if(photo == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<PhotoDTO> updatePhoto(@RequestBody @Valid PhotoDTO photoDTO, int id){
        Photo photo = photoService.update(id, convertToPhoto(photoDTO));
        if(photo == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(convertToPhotoDTO(photo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhoto(@PathVariable int id){
        photoService.delete(id);
        return ResponseEntity.noContent().build();
    }


    private PhotoDTO convertToPhotoDTO(Photo photo){
        return modelMapper.map(photo, PhotoDTO.class);
    }

    private Photo convertToPhoto(PhotoDTO photoDTO){
        return modelMapper.map(photoDTO, Photo.class);
    }
}
