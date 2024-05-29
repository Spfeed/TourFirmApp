package ru.besttours.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.besttours.tour.models.DynamicTourBid;
import ru.besttours.tour.models.DynamicTourUserId;
import ru.besttours.tour.repo.DynamicTourBidRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DynamicTourBidService {

    private final DynamicTourBidRepository dynamicTourBidRepository;

    @Autowired
    public DynamicTourBidService(DynamicTourBidRepository dynamicTourBidRepository) {
        this.dynamicTourBidRepository = dynamicTourBidRepository;
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
}
