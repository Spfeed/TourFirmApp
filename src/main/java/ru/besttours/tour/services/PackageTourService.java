package ru.besttours.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.besttours.tour.models.PackageTour;
import ru.besttours.tour.repo.PackageTourRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PackageTourService {

    private final PackageTourRepository packageTourRepository;

    @Autowired
    public PackageTourService(PackageTourRepository packageTourRepository) {
        this.packageTourRepository = packageTourRepository;
    }

    //BASED CRUD

    public List<PackageTour> findAll() {
        return packageTourRepository.findAll();
    }

    public PackageTour findOne(int id) {
        return packageTourRepository.findById(id).orElse(null);
    }

    @Transactional
    public PackageTour create(PackageTour packageTour) {
        return packageTourRepository.save(packageTour);
    }

    @Transactional
    public PackageTour update(int id, PackageTour updatedPackageTour) {
        updatedPackageTour.setId(id);
        return packageTourRepository.save(updatedPackageTour);
    }

    @Transactional
    public void delete(int id){
        packageTourRepository.deleteById(id);
    }

    //OTHER METHODS

    public PackageTour findByName(String name) {
        return packageTourRepository.findByName(name).orElse(null);
    }
}
