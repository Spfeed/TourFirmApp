package ru.besttours.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.besttours.tour.models.*;
import ru.besttours.tour.models.Number;
import ru.besttours.tour.repo.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class DynamicTourService {

    private final DynamicTourRepository dynamicTourRepository;
    private final DynamicTourBidRepository dynamicTourBidRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final FoodTypeRepository foodTypeRepository;
    private final HotelRepository hotelRepository;
    private final NumberRepository numberRepository;

    @Autowired
    public DynamicTourService(DynamicTourRepository dynamicTourRepository, DynamicTourBidRepository dynamicTourBidRepository, UserRepository userRepository, CityRepository cityRepository, FoodTypeRepository foodTypeRepository, HotelRepository hotelRepository, NumberRepository numberRepository) {
        this.dynamicTourRepository = dynamicTourRepository;
        this.dynamicTourBidRepository = dynamicTourBidRepository;
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.foodTypeRepository = foodTypeRepository;
        this.hotelRepository = hotelRepository;
        this.numberRepository = numberRepository;
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

    @Transactional
    public void addDynTourWithBid (int userId, int cityStart, int cityEnd, int hotelId, int numberId,
                                   int numAdults, int numChildren, int foodTypeId, int duration, String description,
                                   Date dateStart) {

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Пользваотель не найден"));
        City startCity = cityRepository.findById(cityStart).orElseThrow(() -> new IllegalArgumentException("Город начала с таким id не найден"));
        City endCity = cityRepository.findById(cityEnd).orElseThrow(() -> new IllegalArgumentException("Город назанчения с таким id не найден"));
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new IllegalArgumentException("Отель с таким id не найден"));
        Number number = numberRepository.findById(numberId).orElseThrow(() -> new IllegalArgumentException("Номер с таким id не найден"));
        FoodType foodType = foodTypeRepository.findById(foodTypeId).orElseThrow(() -> new IllegalArgumentException("Тип питания с таким id не найден!"));

        DynamicTour dynamicTour = new DynamicTour(duration, numAdults, numChildren, description, dateStart, number, startCity,
                endCity, foodType);

        dynamicTourRepository.save(dynamicTour);

        createBid(userId, dynamicTour.getId());
    }

    @Transactional
    public void createBid(int userId, int dynamicTourId) {
        DynamicTourBid dynamicTourBid = new DynamicTourBid(new DynamicTourUserId(userId, dynamicTourId), false, new Timestamp(System.currentTimeMillis()));
        dynamicTourBidRepository.save(dynamicTourBid);
    }

}
