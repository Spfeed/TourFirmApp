package ru.besttours.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.besttours.tour.models.AccessLevel;
import ru.besttours.tour.repo.AccessLevelRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AccessLevelService {

    private final AccessLevelRepository accessLevelRepository;

    @Autowired
    public AccessLevelService(AccessLevelRepository accessLevelRepository) {
        this.accessLevelRepository = accessLevelRepository;
    }

    //BASED CRUD

    public List<AccessLevel> findAll() {
        return accessLevelRepository.findAll();
    }

    public AccessLevel findOne(int id) {
        return accessLevelRepository.findById(id).orElse(null);
    }

    @Transactional
    public AccessLevel create(AccessLevel accessLevel) {
        return accessLevelRepository.save(accessLevel);
    }

    @Transactional
    public AccessLevel update(int id, AccessLevel updatedAccessLevel) {
        updatedAccessLevel.setId(id);
        return accessLevelRepository.save(updatedAccessLevel);
    }

    @Transactional
    public void delete(int id) {
        accessLevelRepository.deleteById(id);
    }
}
