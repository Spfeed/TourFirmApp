package ru.besttours.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.besttours.tour.dto.PackageTourDTOForMainPC;
import ru.besttours.tour.models.PackageTour;
import ru.besttours.tour.models.PackageTourBid;
import ru.besttours.tour.models.PackageTourUserId;
import ru.besttours.tour.models.User;
import ru.besttours.tour.repo.PackageTourBidRepository;
import ru.besttours.tour.repo.PackageTourRepository;
import ru.besttours.tour.repo.UserRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PackageTourBidService {

    private final PackageTourBidRepository packageTourBidRepository;
    private final UserRepository userRepository;
    private final PackageTourRepository packageTourRepository;

    @Autowired
    public PackageTourBidService(PackageTourBidRepository packageTourBidRepository, UserRepository userRepository, PackageTourRepository packageTourRepository) {
        this.packageTourBidRepository = packageTourBidRepository;
        this.userRepository = userRepository;
        this.packageTourRepository = packageTourRepository;
    }

    //BASED CRUD

    public List<PackageTourBid> findAll() {
        return packageTourBidRepository.findAll();
    }

    public PackageTourBid findOne(PackageTourUserId id) {
        return packageTourBidRepository.findById(id).orElse(null);
    }

    @Transactional
    public void createPackageTourBid(int userId, int packageTourId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Пользователь не найден!"));
        PackageTour packageTour = packageTourRepository.findById(packageTourId)
                .orElseThrow(() -> new IllegalArgumentException("Тур не найден!"));

        PackageTourUserId packageTourUserId = new PackageTourUserId(userId, packageTourId);
        PackageTourBid packageTourBid = new PackageTourBid(packageTourUserId, false, new Timestamp(System.currentTimeMillis()));

        packageTourBidRepository.save(packageTourBid);
    }

    @Transactional
    public PackageTourBid update(PackageTourUserId id, PackageTourBid updatedPackageTourBid) {
        updatedPackageTourBid.setId(id);
        return packageTourBidRepository.save(updatedPackageTourBid);
    }

    @Transactional
    public void delete(PackageTourUserId id) {
        packageTourBidRepository.deleteById(id);
    }

    //OTHER METHODS

    public int getBidsForUserCount (int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с таким id не существует"));
        List<PackageTourBid> bids = packageTourBidRepository.findByIdUserId(userId);
        return bids.size();
    }

    public List<PackageTourDTOForMainPC> getToursForPC (int userId, int numberOf) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с таким id не найден!"));

        List<PackageTourBid> bids = packageTourBidRepository.findByIdUserId(userId);
        List<PackageTourBid> acceptedBids = new ArrayList<>();

        for (PackageTourBid bid : bids) {
            if (bid.isStatus()) {
                acceptedBids.add(bid);
            }
        }

        List<PackageTour> tours = acceptedBids.stream()
                .map(bid-> packageTourRepository.findById(bid.getId().getPackageTourId())
                        .orElseThrow(() -> new IllegalArgumentException("Тур с таким id не найден!")))
                .limit(numberOf)
                .collect(Collectors.toList());

        List<PackageTourDTOForMainPC> toursDTO = new ArrayList<>();

        for (PackageTour tour : tours) {
            PackageTourDTOForMainPC tourDTO= new PackageTourDTOForMainPC();
            tourDTO.setId(tour.getId());
            tourDTO.setPhoto(tour.getEndPlace().getPhotos().stream().findFirst().get().getFilePath());
            tourDTO.setCountryName(tour.getEndPlace().getCountry().getName());
            tourDTO.setCityName(tour.getEndPlace().getName());
            tourDTO.setDateStart(tour.getDateStart());
            tourDTO.setDuration(tour.getDuration());

            toursDTO.add(tourDTO);
        }
        return toursDTO;
    }

    @Transactional
    public void updatePackageTourStatus (int tourId, int userId, boolean status) {
        User user= userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с таким id не найден!"));

        PackageTour tour = packageTourRepository.findById(tourId)
                .orElseThrow(() -> new IllegalArgumentException("Тур с таким id не найден!"));

        PackageTourUserId tourUserId = new PackageTourUserId(user.getId(), tour.getId());
        PackageTourBid bid = packageTourBidRepository.findById(tourUserId)
                .orElseThrow(() -> new IllegalArgumentException("Заявка с таким пользователем и туром не найдена!"));

        bid.setStatus(status);

        packageTourBidRepository.save(bid);
    }

    //OTHER METHODS
}
