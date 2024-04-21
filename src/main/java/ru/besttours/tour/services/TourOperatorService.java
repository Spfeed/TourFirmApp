package ru.besttours.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.besttours.tour.models.TourOperator;
import ru.besttours.tour.repo.TourOperatorRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TourOperatorService {

    private final TourOperatorRepository tourOperatorRepository;

    @Autowired
    public TourOperatorService(TourOperatorRepository tourOperatorRepository) {
        this.tourOperatorRepository = tourOperatorRepository;
    }

    public List<TourOperator> findAll() {
        return tourOperatorRepository.findAll();
    }

    public TourOperator findOne(int id) {
        return tourOperatorRepository.findById(id).orElse(null);
    }

    @Transactional
    public TourOperator create(TourOperator tourOperator) {
        return tourOperatorRepository.save(tourOperator);
    }

    @Transactional
    public TourOperator update(int id, TourOperator updatedTourOperator) {
        updatedTourOperator.setId(id);
        return tourOperatorRepository.save(updatedTourOperator);
    }

    @Transactional
    public void delete(int id) {
        tourOperatorRepository.deleteById(id);
    }
}
