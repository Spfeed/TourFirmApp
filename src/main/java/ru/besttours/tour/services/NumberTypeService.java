package ru.besttours.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.besttours.tour.models.NumberType;
import ru.besttours.tour.repo.NumberTypeRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class NumberTypeService {

    private final NumberTypeRepository numberTypeRepository;

    @Autowired
    public NumberTypeService(NumberTypeRepository numberTypeRepository) {
        this.numberTypeRepository = numberTypeRepository;
    }

    public List<NumberType> findAll() {
        return numberTypeRepository.findAll();
    }

    public NumberType findOne(int id) {
        return numberTypeRepository.findById(id).orElse(null);
    }

    @Transactional
    public NumberType create(NumberType  numberType) {
        return numberTypeRepository.save(numberType);
    }

    @Transactional
    public NumberType update(int id, NumberType updatedNumberType) {
        updatedNumberType.setId(id);
        return numberTypeRepository.save(updatedNumberType);
    }

    @Transactional
    public void delete(int id) {
        numberTypeRepository.deleteById(id);
    }
}
