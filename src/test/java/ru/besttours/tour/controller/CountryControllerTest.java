//package ru.besttours.tour.controller;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.modelmapper.ModelMapper;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import ru.besttours.tour.dto.CountryDTO;
//import ru.besttours.tour.models.Country;
//import ru.besttours.tour.services.CountryService;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//
//public class CountryControllerTest {
//
//    @Mock
//    private CountryService countryService;
//
//    @Mock
//    private ModelMapper modelMapper;
//
//    @InjectMocks
//    private CountryController countryController;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testCreateCountry_Success() {
//        // Создание тестовых данных
//        CountryDTO countryDTO = new CountryDTO();
//        countryDTO.setName("Test Country");
//        countryDTO.setDescription("Test description");
//        countryDTO.setVisa(false);
//        countryDTO.setLanguage("Test language");
//        countryDTO.setCurrency("Test currency");
//        countryDTO.setLocalTime("Test localtime");
//        countryDTO.setReligion("Test religion");
//
//        // Ожидаемый объект страны после создания
//        Country expectedCountry = new Country();
//        expectedCountry.setName("Test Country");
//        expectedCountry.setDescription("Test description");
//        expectedCountry.setVisa(false);
//        expectedCountry.setLanguage("Test language");
//        expectedCountry.setCurrency("Test currency");
//        expectedCountry.setLocalTime("Test localtime");
//        expectedCountry.setReligion("Test religion");
//
//        // Установка поведения макета сервиса
//        when(modelMapper.map(any(CountryDTO.class), any(Class.class))).thenReturn(expectedCountry);
//        when(countryService.create(any(Country.class))).thenReturn(expectedCountry);
//
//        // Вызов метода контроллера
//        ResponseEntity<HttpStatus> response = countryController.createCountry(countryDTO);
//
//        // Проверка результата
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    public void testCreateCountry_BadRequest() {
//        // Создание тестовых данных с некорректными значениями
//        CountryDTO countryDTO = new CountryDTO();
//
//        // Установка поведения макета сервиса
//        when(countryService.create(any(Country.class))).thenReturn(null);
//
//        // Вызов метода контроллера
//        ResponseEntity<HttpStatus> response = countryController.createCountry(countryDTO);
//
//        // Проверка результата
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//    }
//}