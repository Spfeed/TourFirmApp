package ru.besttours.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.besttours.tour.models.Number;
import ru.besttours.tour.repo.NumberRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class NumberService {

    private final NumberRepository numberRepository;

    @Autowired
    public NumberService(NumberRepository numberRepository) {
        this.numberRepository = numberRepository;
    }

    public List<Number> findAll() {
        return numberRepository.findAll();
    }

    public Number findOne(int id) {
        return numberRepository.findById(id).orElse(null);
    }

    @Transactional
    public Number create(Number number) {
        return numberRepository.save(number);
    }

    @Transactional
    public Number update(int id, Number updatedNumber) {
        updatedNumber.setId(id);
        return numberRepository.save(updatedNumber);
    }

    @Transactional
    public void delete(int id) {
        numberRepository.deleteById(id);
    }
}
