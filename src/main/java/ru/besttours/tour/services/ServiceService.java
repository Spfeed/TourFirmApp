package ru.besttours.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.besttours.tour.dto.PcCrudDTO;
import ru.besttours.tour.repo.ServiceRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ServiceService {

    private final ServiceRepository serviceRepository;

    @Autowired
    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    //BASED CRUD

    public List<ru.besttours.tour.models.Service> findAll() {
        return serviceRepository.findAll();
    }

    public ru.besttours.tour.models.Service findOne(int id) {
        return serviceRepository.findById(id).orElse(null);
    }

    @Transactional
    public ru.besttours.tour.models.Service create(ru.besttours.tour.models.Service service) {
        return serviceRepository.save(service);
    }

    @Transactional
    public ru.besttours.tour.models.Service update(int id, ru.besttours.tour.models.Service updatedService) {
        updatedService.setId(id);
        return serviceRepository.save(updatedService);
    }

    @Transactional
    public void delete(int id) {
        serviceRepository.deleteById(id);
    }

    //OTHER METHODS

    public ru.besttours.tour.models.Service findByName(String name) {
        return serviceRepository.findByName(name).orElse(null);
    }

    public List<PcCrudDTO> getServiceForCrudPC() {
        List<ru.besttours.tour.models.Service> services = serviceRepository.findAll();
        List<PcCrudDTO> dtos = new ArrayList<>();

        for (ru.besttours.tour.models.Service service : services) {
            PcCrudDTO dto = new PcCrudDTO();
            dto.setId(service.getId());
            dto.setName(service.getName());
            dtos.add(dto);
        }
        return dtos;
    }
}
