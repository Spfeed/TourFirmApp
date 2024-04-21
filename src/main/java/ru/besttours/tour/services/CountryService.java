package ru.besttours.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.besttours.tour.models.City;
import ru.besttours.tour.models.Country;
import ru.besttours.tour.repo.CountryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    //BASED CRUD

    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    public Country findOne(int id) {
        return countryRepository.findById(id).orElse(null);
    }

    @Transactional
    public Country create(Country country) {
        return countryRepository.save(country);
    }

    @Transactional
    public Country update(int id, Country updatedCountry) {
        updatedCountry.setId(id);
        return countryRepository.save(updatedCountry);
    }

    @Transactional
    public void delete(int id) {
        countryRepository.deleteById(id);
    }

    //OTHER METHODS

    public Country findByName(String name) {
        return countryRepository.findByName(name).orElse(null);
    }

    public List<String> findCities(Country country) {
        List<String> citiesName = new ArrayList<>();
        for (City city : country.getCities()){
            citiesName.add(city.getName());
        }
        return citiesName;
    }
}
