package ru.besttours.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import ru.besttours.tour.dto.CountryForCountryPageDTO;
import ru.besttours.tour.dto.CountryForModalDTO;
import ru.besttours.tour.dto.CountryForSearchFormDTO;
import ru.besttours.tour.models.City;
import ru.besttours.tour.models.Country;
import ru.besttours.tour.repo.CountryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<String> findAllNames() {
        List<Country> countries= countryRepository.findAll();
        return countries.stream() .filter(country -> !country.getName().equals("Россия"))
                .map(Country::getName).collect(Collectors.toList());
    }

    public CountryForCountryPageDTO getCountryInfo (String countryName) {
        Country country = countryRepository.findByName(countryName).orElseThrow(() -> new RuntimeException("Страна не найдена"));

        CountryForCountryPageDTO countryForCountryPageDTO = new CountryForCountryPageDTO();
        countryForCountryPageDTO.setName(country.getName());
        countryForCountryPageDTO.setDescription(country.getDescription());
        countryForCountryPageDTO.setVisa(country.isVisa());
        countryForCountryPageDTO.setLanguage(country.getLanguage());
        countryForCountryPageDTO.setCurrency(country.getCurrency());
        countryForCountryPageDTO.setLocalTime(country.getLocalTime());
        countryForCountryPageDTO.setReligion(country.getReligion());

        if (!country.getPhotos().isEmpty()) {
            countryForCountryPageDTO.setCountryPhoto(country.getPhotos().iterator().next().getFilePath());
        }

        List<String> cityPhotos = country.getCities().stream()
                .flatMap(city -> city.getPhotos().stream().findFirst().stream())
                .limit(3)
                .map(photo -> photo.getFilePath())
                .collect(Collectors.toList());

        countryForCountryPageDTO.setCityPhotos(cityPhotos);

        return countryForCountryPageDTO;
    }

    public List<CountryForSearchFormDTO> getCountriesForSearchForm () {
        List<CountryForSearchFormDTO> dtos = new ArrayList<>();
        List<Country> countries = countryRepository.findAll().stream()
                .filter(country -> !country.getName().equals("Россия"))
                .collect(Collectors.toList());
        for (Country country : countries) {
            dtos.add(new CountryForSearchFormDTO(country.getId(), country.getName()));
        }

        return dtos;
    }

    public List<CountryForModalDTO> getCitiesForModal(int countryId) {
        Country country = countryRepository.findById(countryId).orElseThrow(() ->new RuntimeException("Страна не найдена"));
        List<City> cities = country.getCities();

        List<CountryForModalDTO> dtos = new ArrayList<>();
        for (City city : cities){
            CountryForModalDTO dto = new CountryForModalDTO();
            dto.setCityId(city.getId());
            dto.setCityName(city.getName());
            dtos.add(dto);
        }

        return dtos;
    }
}
