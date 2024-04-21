package ru.besttours.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.besttours.tour.models.Hotel;
import ru.besttours.tour.repo.HotelRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class HotelService {

    private final HotelRepository hotelRepository;
    private final CityService cityService;

    @Autowired
    public HotelService(HotelRepository hotelRepository, CityService cityService) {
        this.hotelRepository = hotelRepository;
        this.cityService = cityService;
    }

    //BASED CRUD

    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    public Hotel findOne(int id) {
        return hotelRepository.findById(id).orElse(null);
    }

    @Transactional
    public Hotel create(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Transactional
    public Hotel update(int id, Hotel updatedHotel) {
        updatedHotel.setId(id);
        return hotelRepository.save(updatedHotel);
    }

    @Transactional
    public void delete(int id) {
        hotelRepository.deleteById(id);
    }

    //OTHER METHODS

    public Hotel findByName(String name) {
        return hotelRepository.findByName(name).orElse(null);
    }

}
