package ru.besttours.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.besttours.tour.repo.ServiceRepository;

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
}
