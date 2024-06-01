package ru.besttours.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.besttours.tour.models.*;
import ru.besttours.tour.repo.DynamicTourBidRepository;
import ru.besttours.tour.repo.DynamicTourRepository;
import ru.besttours.tour.repo.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DynamicTourBidService {

    private final DynamicTourBidRepository dynamicTourBidRepository;
    private final UserRepository userRepository;
    private final DynamicTourRepository dynamicTourRepository;

    @Autowired
    public DynamicTourBidService(DynamicTourBidRepository dynamicTourBidRepository, UserRepository userRepository, DynamicTourRepository dynamicTourRepository) {
        this.dynamicTourBidRepository = dynamicTourBidRepository;
        this.userRepository = userRepository;
        this.dynamicTourRepository = dynamicTourRepository;
    }

    //BASED CRUD

    public List<DynamicTourBid> findAll() {
        return dynamicTourBidRepository.findAll();
    }

    public DynamicTourBid findOne(DynamicTourUserId id) {
        return dynamicTourBidRepository.findById(id).orElse(null);
    }

    @Transactional
    public DynamicTourBid create(DynamicTourBid dynamicTourBid) {
        return dynamicTourBidRepository.save(dynamicTourBid);
    }

    @Transactional
    public DynamicTourBid update(DynamicTourUserId id ,DynamicTourBid updatedDynamicTourBid) {
        updatedDynamicTourBid.setId(id);
        return dynamicTourBidRepository.save(updatedDynamicTourBid);
    }

    @Transactional
    public void delete(DynamicTourUserId id) {
        dynamicTourBidRepository.deleteById(id);
    }

    @Transactional
    public void updatePackageTourStatus (int tourId, int userId, boolean status) {
        User user= userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с таким id не найден!"));

        DynamicTour tour = dynamicTourRepository.findById(tourId)
                .orElseThrow(() -> new IllegalArgumentException("Тур с таким id не найден!"));

        DynamicTourUserId tourUserId = new DynamicTourUserId(user.getId(), tour.getId());
        DynamicTourBid bid = dynamicTourBidRepository.findById(tourUserId)
                .orElseThrow(() -> new IllegalArgumentException("Заявка с таким пользователем и туром не найдена!"));

        bid.setStatus(status);

        dynamicTourBidRepository.save(bid);
    }
}
