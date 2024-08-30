//package ru.besttours.tour.services;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import ru.besttours.tour.dto.PackageTourForModalDTO;
//import ru.besttours.tour.models.*;
//import ru.besttours.tour.models.Number;
//import ru.besttours.tour.repo.CityRepository;
//import ru.besttours.tour.repo.CountryRepository;
//import ru.besttours.tour.repo.PackageTourRepository;
//
//import java.math.BigDecimal;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//class PackageTourServiceTest {
//
//    @Mock
//    private PackageTourRepository packageTourRepository;
//
//    @Mock
//    private CityRepository cityRepository;
//
//    @Mock
//    private CountryRepository countryRepository;
//
//    @InjectMocks
//    private PackageTourService packageTourService;
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testPackageToursForModal() {
//        // Создаем тестовые данные
//        Country country = new Country();
//        country.setId(1);
//        List<City> cities = new ArrayList<>();
//        country.setCities(cities);
//        country.setName("Test Country");
//
//        City city1 = new City();
//        city1.setId(1);
//        city1.setName("Test City 1");
//        city1.setCountry(country);
//
//        City city2 = new City();
//        city2.setId(2);
//        city2.setName("Test City 2");
//        city2.setCountry(country);
//
//        Photo photo1 = new Photo();
//        photo1.setFilePath("path/to/photo1");
//
//        Photo photo2 = new Photo();
//        photo2.setFilePath("path/to/photo2");
//
//        Set<Photo>photos1 = new HashSet<>();
//        Set<Photo>photos2 = new HashSet<>();
//        photos1.add(photo1);
//        photos2.add(photo2);
//
//        city1.setPhotos(photos1);
//        city2.setPhotos(photos2);
//
//        cities.add(city1);
//        cities.add(city2);
//
//        Hotel hotel1 = new Hotel();
//        hotel1.setName("Test Hotel 1");
//        hotel1.setRating(BigDecimal.valueOf(5));
//
//        Number number1 = new Number();
//        number1.setHotel(hotel1);
//
//        Hotel hotel2 = new Hotel();
//        hotel2.setName("Test Hotel 2");
//        hotel2.setRating(BigDecimal.valueOf(5));
//
//        Number number2 = new Number();
//        number2.setHotel(hotel2);
//
//        FoodType foodType1= new FoodType();
//        foodType1.setId(1);
//        foodType1.setName("food type 1");
//        FoodType foodType2= new FoodType();
//        foodType2.setId(2);
//        foodType2.setName("food type 2");
//
//        PackageTour tour1 = new PackageTour();
//        tour1.setId(1);
//        tour1.setName("Test Tour 1");
//        tour1.setEndPlace(city1);
//        tour1.setNumber(number1);
//        tour1.setCostPack(BigDecimal.valueOf(1000));
//        tour1.setDateStart(new Date());
//        tour1.setDuration(7);
//        tour1.setFoodType(foodType1);
//
//        PackageTour tour2 = new PackageTour();
//        tour2.setId(2);
//        tour2.setName("Test Tour 2");
//        tour2.setEndPlace(city2);
//        tour2.setNumber(number2);
//        tour2.setCostPack(BigDecimal.valueOf(1500));
//        tour2.setDateStart(new Date());
//        tour2.setDuration(10);
//        tour2.setFoodType(foodType2);
//
//        List<PackageTour> packageTours = Arrays.asList(tour1, tour2);
//
//        when(countryRepository.findById(1)).thenReturn(Optional.ofNullable(country));
//        when(cityRepository.findByCountryName("Test Country")).thenReturn(Arrays.asList(city1, city2));
//        when(packageTourRepository.findToursByParameters(0, 1, null, null, 0, 0, 0)).thenReturn(Collections.singletonList(tour1));
//        when(packageTourRepository.findToursByParameters(0, 2, null, null, 0, 0, 0)).thenReturn(Collections.singletonList(tour2));
//
//        // Вызываем метод, который тестируем
//        List<PackageTourForModalDTO> result = packageTourService.packageToursForModal(0, 1, null, null, 0, 0, 0);
//
//        // Проверяем результат
//        assertEquals(1, result.size());
//        PackageTourForModalDTO dto = result.get(0);
//        assertEquals(1, dto.getId());
//        assertEquals("Test Tour 1", dto.getName());
//        assertEquals("Test City 1", dto.getCityName());
//        assertEquals(BigDecimal.valueOf(1000), dto.getCostPack());
//        assertEquals(7, dto.getDuration());
//        // Проверяем, что другие поля также заполнены правильно
//    }
//}
