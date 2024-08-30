package ru.besttours.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.besttours.tour.dto.*;
import ru.besttours.tour.models.*;
import ru.besttours.tour.repo.CityRepository;
import ru.besttours.tour.repo.CountryRepository;
import ru.besttours.tour.repo.PackageTourRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PackageTourService {

    private final PackageTourRepository packageTourRepository;
    private final CountryRepository countryRepository;

    private final CityRepository cityRepository;

    @Autowired
    public PackageTourService(PackageTourRepository packageTourRepository, CountryRepository countryRepository,
                              CityRepository cityRepository) {
        this.packageTourRepository = packageTourRepository;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
    }

    //BASED CRUD

    public List<PackageTour> findAll() {
        return packageTourRepository.findAll();
    }

    public PackageTour findOne(int id) {
        return packageTourRepository.findById(id).orElse(null);
    }

    @Transactional
    public PackageTour create(PackageTour packageTour) {
        return packageTourRepository.save(packageTour);
    }

    @Transactional
    public PackageTour update(int id, PackageTour updatedPackageTour) {
        updatedPackageTour.setId(id);
        return packageTourRepository.save(updatedPackageTour);
    }

    @Transactional
    public void delete(int id){
        packageTourRepository.deleteById(id);
    }

    //OTHER METHODS

    public PackageTour findByName(String name) {
        return packageTourRepository.findByName(name).orElse(null);
    }

    public List<PackageTourForCountryDTO> getToursByCountry(String countryName) {

        List<City> cities = cityRepository.findByCountryName(countryName);
        List<PackageTour> tours = cities.stream()
                .flatMap(city -> packageTourRepository.findByEndPlace(city).stream())
                .limit(4)
                .collect(Collectors.toList());

        Map<City, Integer> cityPhotoIndexMap = new HashMap<>();
        return convertToDTO(tours, cityPhotoIndexMap);
    }

    public List<PackageTourForCountryDTO> getToursByCity(String cityName) {
        City city = cityRepository.findByName(cityName).orElseThrow(() -> new IllegalArgumentException("Город не найден"));
        List<PackageTour> tours = city.getBeginPlacePackageTours();
        List<PackageTour> limitedTours = new ArrayList<>();
        limitedTours = tours.stream().limit(3).collect(Collectors.toList());
        System.out.println(limitedTours);
        Map<City, Integer> cityPhotoIndexMap = new HashMap<>();
        return convertToDTO(limitedTours, cityPhotoIndexMap);
    }

    public PackageTourForCountryDTO getActualTourForCity (String cityName) {
        City city = cityRepository.findByName(cityName).orElseThrow(() -> new IllegalArgumentException("Город не найден"));
        List<PackageTour> tours = city.getBeginPlacePackageTours();
        PackageTour actualTour = tours.get(tours.size()-1);
        //TODO заменить последний элемент на тур с наибольшим количеством броней.
        return convertToActualToursDTO(actualTour);
    }

    public List<PackageTourForModalDTO> packageToursForModal (int fromId, int toId, Date startDate, Date endDate,
                                                              int days, int adults, int children) {

        Country country = countryRepository.findById(toId).orElseThrow(() -> new IllegalArgumentException("Страна не найдена"));
        List<City> cities = country.getCities();

        List<PackageTour> tours = new ArrayList<>();

        for (City city : cities) {
            List<PackageTour> cityTours = packageTourRepository.findToursByParameters(fromId, city.getId(), startDate, endDate,
                    days, adults, children);
            tours.addAll(cityTours);
        }

        return convertToModalDTO(tours);
    }

    public PackageTourForTourPageDTO getTourInfo (int id) {
        PackageTour tour = packageTourRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Тур с таким id не найден!"));

        PackageTourForTourPageDTO dto = new PackageTourForTourPageDTO();

        dto.setId(tour.getId());
        dto.setCityFrom(tour.getBeginPlace().getName());
        dto.setDateStart(tour.getDateStart());
        dto.setCityTo(tour.getEndPlace().getName());
        dto.setCountryTo(tour.getEndPlace().getCountry().getName());
        dto.setDuration(tour.getDuration());
        dto.setNumAdults(tour.getNumAdults());
        dto.setNumChildren(tour.getNumChildren());
        dto.setNumberName(tour.getNumber().getNumberType().getName());
        dto.setFoodType(tour.getFoodType().getName());

        Hotel hotel = tour.getNumber().getHotel();
        Set<Photo>photos = hotel.getPhotos();
        List<String>hotelPhotos = new ArrayList<>();

        for (Photo photo : photos) {
            hotelPhotos.add(photo.getFilePath());
        }

        dto.setHotelPhotos(hotelPhotos);
        dto.setHotelName(hotel.getName());
        dto.setHotelRating(hotel.getRating());
        dto.setBeachLine(hotel.getBeachLine());
        dto.setHotelInfo(hotel.getInformation());
        dto.setHotelService(hotel.getServices());

        Set<ru.besttours.tour.models.Service> services = tour.getServices();
        List<String> tourServices = new ArrayList<>();

        for (ru.besttours.tour.models.Service service : services) {
            tourServices.add(service.getName());
        }

        dto.setTourServices(tourServices);

        return dto;
    }

    public PackageTourForHistoryDTO getTourInfoForHistory (int id) {
        PackageTour packageTour = packageTourRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Тур с таким id не найден"));

        PackageTourForHistoryDTO dto = new PackageTourForHistoryDTO();

        dto.setTourName(packageTour.getName());
        dto.setDateStart(packageTour.getDateStart());
        dto.setDuration(packageTour.getDuration());
        dto.setNumAdults(packageTour.getNumAdults());
        dto.setNumChildren(packageTour.getNumChildren());
        dto.setCityStart(packageTour.getBeginPlace().getName());
        dto.setCityEnd(packageTour.getEndPlace().getName());
        dto.setHotelName(packageTour.getNumber().getHotel().getName());
        dto.setNumberName(packageTour.getNumber().getNumberType().getName());
        dto.setFoodTypeName(packageTour.getFoodType().getName());
        dto.setTourOp(packageTour.getTourOperator().getName());
        dto.setTransfer(packageTour.getTransfer().getCompany());

        Set<ru.besttours.tour.models.Service> services = packageTour.getServices();

        List<String> servicesName = new ArrayList<>();

        for (ru.besttours.tour.models.Service service : services) {
            servicesName.add(service.getName());
        }

        dto.setServices(servicesName);

        return dto;
    }

    public List<PcCrudDTO> getTourForCrudPC() {
        List<PackageTour> packageTours = packageTourRepository.findAll();
        List<PcCrudDTO> dtos = new ArrayList<>();

        for (PackageTour packageTour: packageTours) {
            PcCrudDTO dto = new PcCrudDTO();
            dto.setId(packageTour.getId());
            dto.setName(packageTour.getName());
            dtos.add(dto);
        }
        return dtos;
    }

    //PRIVATE METHODS

    private List<PackageTourForModalDTO> convertToModalDTO (List<PackageTour> tours) {
        List<PackageTourForModalDTO> dtos = new ArrayList<>();

        for (PackageTour tour : tours) {

            PackageTourForModalDTO dto = new PackageTourForModalDTO();
            dto.setId(tour.getId());
            dto.setName(tour.getName());
            dto.setCityName(tour.getEndPlace().getName());
            dto.setHotelName(tour.getNumber().getHotel().getName());
            dto.setHotelRating(tour.getNumber().getHotel().getRating());
            dto.setCostPack(tour.getCostPack());
            dto.setDateStart(tour.getDateStart());
            dto.setDuration(tour.getDuration());
            dto.setCityPhoto(tour.getEndPlace().getPhotos().stream().findFirst().get().getFilePath());
            dto.setFoodId(tour.getFoodType().getId());
            dtos.add(dto);
        }

        return dtos;
    }

    private List<PackageTourForCountryDTO> convertToDTO(List<PackageTour> tours, Map<City, Integer> cityPhotoIndexMap) {
        List<PackageTourForCountryDTO> tourDTOs = new ArrayList<>();

        for (PackageTour packageTour: tours) {

            PackageTourForCountryDTO dto = new PackageTourForCountryDTO();
            dto.setId(packageTour.getId());
            dto.setName(packageTour.getName());
            dto.setNumAdults(packageTour.getNumAdults());
            dto.setNumChildren(packageTour.getNumChildren());
            dto.setDateStart(packageTour.getDateStart());
            dto.setDuration(packageTour.getDuration());
            dto.setCostPack(packageTour.getCostPack());
            dto.setCityName(packageTour.getEndPlace().getName());
            dto.setCountryName(packageTour.getEndPlace().getCountry().getName());

            City city = packageTour.getEndPlace();
            String cityPhoto =getNextPhoto(city, cityPhotoIndexMap);
            dto.setCityPhoto(cityPhoto);

            tourDTOs.add(dto);
        }

        return tourDTOs;
    }

    private String getNextPhoto(City city, Map<City, Integer> cityPhotoIndexMap) {
        Set<Photo> photos = city.getPhotos();
        if (photos.isEmpty()) {
            return null;
        }

        List<Photo> photoList = new ArrayList<>(photos);
        int index = cityPhotoIndexMap.getOrDefault(city, 0);

        String photoPath = photoList.get(index % photoList.size()).getFilePath();
        cityPhotoIndexMap.put(city, index+1);

        return photoPath;
    }

    private PackageTourForCountryDTO convertToActualToursDTO (PackageTour packageTour) {
        PackageTourForCountryDTO dto = new PackageTourForCountryDTO();
        dto.setId(packageTour.getId());
        dto.setName(packageTour.getName());
        dto.setCountryName(packageTour.getEndPlace().getCountry().getName());
        dto.setCityName(packageTour.getEndPlace().getName());
        dto.setDateStart(packageTour.getDateStart());
        dto.setDuration(packageTour.getDuration());
        dto.setCostPack(packageTour.getCostPack());
        dto.setCityPhoto(packageTour.getEndPlace().getPhotos().stream().findFirst().get().getFilePath());
        dto.setNumAdults(packageTour.getNumAdults());
        dto.setNumChildren(packageTour.getNumChildren());

        return dto;
    }

}
