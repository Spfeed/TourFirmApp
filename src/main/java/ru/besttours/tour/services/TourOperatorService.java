package ru.besttours.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.besttours.tour.dto.PcCrudDTO;
import ru.besttours.tour.dto.TourOperatorDTO;
import ru.besttours.tour.models.Photo;
import ru.besttours.tour.models.TourOperator;
import ru.besttours.tour.repo.TourOperatorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    //OTHER METHODS

    public List<TourOperatorDTO> findAllWithPhotos() {
        List<TourOperator> tourOperators = tourOperatorRepository.findAll();
        return tourOperators.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<PcCrudDTO> getTourOpersForCrudPC() {
        List<TourOperator> tourOperators = tourOperatorRepository.findAll();
        List<PcCrudDTO> dtos = new ArrayList<>();

        for (TourOperator tourOperator: tourOperators) {
            PcCrudDTO dto = new PcCrudDTO();
            dto.setId(tourOperator.getId());
            dto.setName(tourOperator.getName());
            dtos.add(dto);
        }
        return dtos;
    }

    private TourOperatorDTO mapToDTO(TourOperator tourOperator) {
        Set<String> photoUrls = tourOperator.getPhotos().stream()
                .map(Photo::getFilePath)
                .collect(Collectors.toSet());
        TourOperatorDTO dto = new TourOperatorDTO();
        dto.setName(tourOperator.getName());
        dto.setDescription(tourOperator.getDescription());
        dto.setSite(tourOperator.getSite());
        dto.setRating(tourOperator.getRating());
        dto.setPhotoUrls(photoUrls);
        return dto;
    }
}
