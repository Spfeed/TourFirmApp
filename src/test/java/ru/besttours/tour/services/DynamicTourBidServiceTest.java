//package ru.besttours.tour.services;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import ru.besttours.tour.models.DynamicTour;
//import ru.besttours.tour.models.DynamicTourBid;
//import ru.besttours.tour.models.DynamicTourUserId;
//import ru.besttours.tour.models.User;
//import ru.besttours.tour.repo.DynamicTourBidRepository;
//import ru.besttours.tour.repo.DynamicTourRepository;
//import ru.besttours.tour.repo.UserRepository;
//import ru.besttours.tour.services.DynamicTourBidService;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//public class DynamicTourBidServiceTest {
//
//    @Mock
//    private DynamicTourBidRepository dynamicTourBidRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private DynamicTourRepository dynamicTourRepository;
//
//    @InjectMocks
//    private DynamicTourBidService dynamicTourBidService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void updatePackageTourStatus() {
//        // Создание тестовых данные
//        int userId = 1;
//        int tourId = 1;
//        boolean status = true;
//        DynamicTourBid bid = new DynamicTourBid();
//        bid.setStatus(false); // Установка начального значение статуса
//
//        User user = new User();
//        user.setId(userId);
//
//        DynamicTour tour = new DynamicTour();
//        tour.setId(tourId);
//
//        // Настройка моков и их возвращаемых значений
//        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
//        when(dynamicTourRepository.findById(tourId)).thenReturn(java.util.Optional.of(tour));
//        when(dynamicTourBidRepository.findById(any())).thenReturn(java.util.Optional.of(bid));
//        when(dynamicTourBidRepository.save(any())).thenReturn(bid);
//
//        // Вызов тестируемого метода
//        dynamicTourBidService.updatePackageTourStatus(tourId, userId, status);
//
//        // Проверка, что метод findById был вызван ровно один раз с аргументом типа DynamicTourUserId
//        verify(dynamicTourBidRepository, times(1)).findById(any(DynamicTourUserId.class));
//
//        // Проверка, что вызван метод save ровно один раз
//        verify(dynamicTourBidRepository, times(1)).save(any());
//
//        // Проверка, что статус заявки был изменен на ожидаемое значение
//        assert bid.isStatus() == status;
//    }
//}
