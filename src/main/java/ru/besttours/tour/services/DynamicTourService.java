package ru.besttours.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.besttours.tour.models.DynamicTour;
import ru.besttours.tour.repo.DynamicTourRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DynamicTourService {

    private final DynamicTourRepository dynamicTourRepository;

    @Autowired
    public DynamicTourService(DynamicTourRepository dynamicTourRepository) {
        this.dynamicTourRepository = dynamicTourRepository;
    }

    //BASED CRUD

    public List<DynamicTour> findAll() {
        return dynamicTourRepository.findAll();
    }

    public DynamicTour findOne(int id) {
        return dynamicTourRepository.findById(id).orElse(null);
    }

    @Transactional
    public DynamicTour create(DynamicTour dynamicTour) {
        return dynamicTourRepository.save(dynamicTour);
    }

    @Transactional
    public DynamicTour update(int id, DynamicTour updatedDynamicTour)  {
        updatedDynamicTour.setId(id);
        return dynamicTourRepository.save(updatedDynamicTour);
    }

    @Transactional
    public void delete(int id) {
        dynamicTourRepository.deleteById(id);
    }
}
