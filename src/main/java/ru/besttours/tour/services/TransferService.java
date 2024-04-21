package ru.besttours.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.besttours.tour.models.Transfer;
import ru.besttours.tour.repo.TransferRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TransferService {

    private final TransferRepository transferRepository;

    @Autowired
    public TransferService(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public List<Transfer> findAll() {
        return transferRepository.findAll();
    }

    public Transfer findOne(int id) {
        return transferRepository.findById(id).orElse(null);
    }

    @Transactional
    public Transfer create(Transfer transfer) {
        return transferRepository.save(transfer);
    }

    @Transactional
    public Transfer update(int id, Transfer updatedTransfer){
        updatedTransfer.setId(id);
        return transferRepository.save(updatedTransfer);
    }

    @Transactional
    public void delete(int id) {
        transferRepository.deleteById(id);
    }
}