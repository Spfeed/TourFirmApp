package ru.besttours.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.besttours.tour.models.PackageTourBid;
import ru.besttours.tour.models.PackageTourUserId;
import ru.besttours.tour.repo.PackageTourBidRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PackageTourBidService {

    private final PackageTourBidRepository packageTourBidRepository;

    @Autowired
    public PackageTourBidService(PackageTourBidRepository packageTourBidRepository) {
        this.packageTourBidRepository = packageTourBidRepository;
    }

    //BASED CRUD

    public List<PackageTourBid> findAll() {
        return packageTourBidRepository.findAll();
    }

    public PackageTourBid findOne(PackageTourUserId id) {
        return packageTourBidRepository.findById(id).orElse(null);
    }

    @Transactional
    public PackageTourBid create(PackageTourBid packageTourBid) {
        return packageTourBidRepository.save(packageTourBid);
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
}
