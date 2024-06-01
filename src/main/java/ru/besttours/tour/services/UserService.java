package ru.besttours.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.besttours.tour.dto.TourBidForPC;
import ru.besttours.tour.models.*;
import ru.besttours.tour.repo.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final PackageTourBidRepository packageTourBidRepository;

    private final DynamicTourBidRepository dynamicTourBidRepository;

    private final PackageTourRepository packageTourRepository;

    private final DynamicTourRepository dynamicTourRepository;

    @Autowired
    public UserService(UserRepository userRepository, PackageTourBidRepository packageTourBidRepository, DynamicTourBidRepository dynamicTourBidRepository, PackageTourRepository packageTourRepository, DynamicTourRepository dynamicTourRepository) {
        this.userRepository = userRepository;
        this.packageTourBidRepository = packageTourBidRepository;
        this.dynamicTourBidRepository = dynamicTourBidRepository;
        this.packageTourRepository = packageTourRepository;
        this.dynamicTourRepository = dynamicTourRepository;
    }

    //BASED CRUD

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOne(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public User create(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User update(int id, User updatedUser) {
        updatedUser.setId(id);
        return userRepository.save(updatedUser);
    }

    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    //OTHER METHODS

    public User findByLastnameAndFirstname(String lastname, String firstname) {
        return userRepository.findByLastNameAndName(lastname, firstname).orElse(null);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public List<TourBidForPC> getBidsForPC(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с таким id не найден"));
        return getBidsForUser(user);
    }

    public List<TourBidForPC> getBidsForAdminToAccept() {
        List<User> users = userRepository.findAll();
        List<TourBidForPC> bids = new ArrayList<>();

        for (User user : users) {
            if (user.getAccessLevel().getName().equals("USER")) {
                bids.addAll(getBidsForUser(user));
            }
        }

        Iterator<TourBidForPC> iterator = bids.iterator();
        while (iterator.hasNext()) {
            TourBidForPC bid = iterator.next();
            if (bid.isAccepted()) {
                iterator.remove();
            }
        }
        return bids;
    }

    public List<TourBidForPC> getBidsForAdminHistory () {
        List<User> users = userRepository.findAll();
        List<TourBidForPC> bids = new ArrayList<>();

        for (User user : users) {
            if (user.getAccessLevel().getName().equals("USER")) {
                bids.addAll(getBidsForUser(user));
            }
        }
        Iterator<TourBidForPC> iterator = bids.iterator();
        while (iterator.hasNext()) {
            TourBidForPC bid = iterator.next();
            if (!bid.isAccepted()) {
                iterator.remove();
            }
        }

        return bids;
    }

    private List<TourBidForPC> getBidsForUser (User user) {
        List<PackageTourBid> bids = packageTourBidRepository.findByIdUserId(user.getId());
        List<DynamicTourBid> dynamicTourBids = dynamicTourBidRepository.findByIdUserId(user.getId());

        List<TourBidForPC> allBids = new ArrayList<>();

        for (PackageTourBid packBid : bids) {
            TourBidForPC tourBid = new TourBidForPC();
            tourBid.setTourId(packBid.getId().getPackageTourId());
            tourBid.setUserId(user.getId());
            tourBid.setUserEmail(user.getEmail());
            tourBid.setDynamic(false);
            PackageTour tour = packageTourRepository.findById(packBid.getId().getPackageTourId())
                    .orElseThrow(()->new IllegalArgumentException("Тур с таким id не найден"));
            tourBid.setCountryName(tour.getEndPlace().getCountry().getName());
            tourBid.setCityName(tour.getEndPlace().getName());
            tourBid.setAccepted(packBid.isStatus());
            tourBid.setTimestamp(packBid.getCreatedAt());

            allBids.add(tourBid);
        }

        for (DynamicTourBid dynBid : dynamicTourBids) {
            TourBidForPC tourBid = new TourBidForPC();
            tourBid.setTourId(dynBid.getId().getDynamicTourId());
            tourBid.setUserId(user.getId());
            tourBid.setUserEmail(user.getEmail());
            tourBid.setDynamic(true);

            DynamicTour tour = dynamicTourRepository.findById(dynBid.getId().getDynamicTourId())
                    .orElseThrow(() -> new IllegalArgumentException("Тур с таким id не найден"));
            tourBid.setCountryName(tour.getEndPlace().getCountry().getName());
            tourBid.setCityName(tour.getEndPlace().getName());
            tourBid.setAccepted(dynBid.isStatus());
            tourBid.setTimestamp(dynBid.getCreated_at());

            allBids.add(tourBid);
        }

        return allBids;
    }
}
