package ru.besttours.tour.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.besttours.tour.dto.ServiceDTO;
import ru.besttours.tour.models.Service;
import ru.besttours.tour.services.ServiceService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/services")
public class ServiceController {

    private final ServiceService serviceService;
    private final ModelMapper modelMapper;

    @Autowired
    public ServiceController(ServiceService serviceService, ModelMapper modelMapper) {
        this.serviceService = serviceService;
        this.modelMapper = modelMapper;
    }

    //BASIC ENDPOINTS

    @GetMapping("/all")
    public List<ServiceDTO> getAllServices(){
        return serviceService.findAll().stream().map(this::convertToServiceDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ServiceDTO getOneService(@PathVariable int id){
        return convertToServiceDTO(serviceService.findOne(id));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createService(@RequestBody @Valid ServiceDTO serviceDTO){
        Service service = serviceService.create(convertToService(serviceDTO));
        if(service == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<ServiceDTO> updateService(@RequestBody @Valid ServiceDTO serviceDTO, int id){
        Service service = serviceService.update(id, convertToService(serviceDTO));
        if(service == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(convertToServiceDTO(service));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable int id){
        serviceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //OTHER ENDPOINTS

    @GetMapping("/find-name/{serviceName}")
    public ServiceDTO findServiceByName(@PathVariable String serviceName){
        return convertToServiceDTO(serviceService.findByName(serviceName));
    }

    private ServiceDTO convertToServiceDTO(Service service){
        return modelMapper.map(service, ServiceDTO.class);
    }

    private Service convertToService(ServiceDTO serviceDTO){
        return modelMapper.map(serviceDTO, Service.class);
    }
}
