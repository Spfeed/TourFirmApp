package ru.besttours.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.besttours.tour.models.Photo;
import ru.besttours.tour.repo.PhotoRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PhotoService {

    private final PhotoRepository photoRepository;

    @Autowired
    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public List<Photo> findAll() {
        return photoRepository.findAll();
    }

    public Photo findOne(int id) {
        return photoRepository.findById(id).orElse(null);
    }

    @Transactional
    public Photo create(Photo photo) {
        return photoRepository.save(photo);
    }

    @Transactional
    public Photo update(int id, Photo updatedPhoto) {
        updatedPhoto.setId(id);
        return photoRepository.save(updatedPhoto);
    }

    @Transactional
    public void delete(int id) {
        photoRepository.deleteById(id);
    }
}
