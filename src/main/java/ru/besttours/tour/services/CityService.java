package ru.besttours.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.besttours.tour.dto.CityDTO;
import ru.besttours.tour.dto.CityForCityDTO;
import ru.besttours.tour.dto.CityForSearchFormDTO;
import ru.besttours.tour.models.City;
import ru.besttours.tour.models.Country;
import ru.besttours.tour.models.Hotel;
import ru.besttours.tour.models.Photo;
import ru.besttours.tour.repo.CityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CityService {

    private final CityRepository cityRepository;
    private final CountryService countryService;

    @Autowired
    public CityService(CityRepository cityRepository, CountryService countryService) {
        this.cityRepository = cityRepository;
        this.countryService = countryService;
    }

    //BASED CRUD

    public List<City> findAll() {
        return cityRepository.findAll();
    }

    public City findOne(int id) {
        return cityRepository.findById(id).orElse(null);
    }

    @Transactional
    public City create(City city) {
        return cityRepository.save(city);
    }

    @Transactional
    public City update(int id, City updatedCity) {
        updatedCity.setId(id);
        return cityRepository.save(updatedCity);
    }

    @Transactional
    public void delete(int id) {
        cityRepository.deleteById(id);
    }

    //OTHER METHODS

    public City findByName(String name){
        return cityRepository.findByName(name).orElse(null);
    }

    public List<String> findAllHotels(City city){
        List<String> hotelsInCity = new ArrayList<>();
        for (Hotel hotel : city.getHotels()){
            hotelsInCity.add(hotel.getName());
        }
        return hotelsInCity;
    }

    public CityForCityDTO getCityInfo (String cityName) {
        City city = cityRepository.findByName(cityName).orElseThrow(() -> new RuntimeException("Город не найден!"));

        CityForCityDTO dto = new CityForCityDTO();
        dto.setName(city.getName());
        dto.setDescription(city.getDescription());

        List<String> photos = city.getPhotos().stream()
                .map(Photo::getFilePath)
                .collect(Collectors.toList());

        String bgPhoto = photos.stream().filter(photo -> photo.contains("bg"))
                .findFirst().orElse(null);

        List<String> otherPhotos = photos.stream().filter(photo -> !photo.contains("bg"))
                .collect(Collectors.toList());

        dto.setPhotoBg(bgPhoto);
        dto.setCityPhotos(otherPhotos);

        return dto;
    }

    public List<CityForSearchFormDTO> getCitiesForSearchForm () {
        List<CityForSearchFormDTO> cityForSearchFormDTOList = new ArrayList<>();
        Country country = countryService.findByName("Россия");
        for (City city : country.getCities()){
            cityForSearchFormDTOList.add(new CityForSearchFormDTO(city.getId(), city.getName()));
        }
        return cityForSearchFormDTOList;
    }
}
