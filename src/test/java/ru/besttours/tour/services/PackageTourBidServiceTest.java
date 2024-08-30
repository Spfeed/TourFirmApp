//package ru.besttours.tour.services;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import ru.besttours.tour.models.PackageTour;
//import ru.besttours.tour.models.PackageTourBid;
//import ru.besttours.tour.models.PackageTourUserId;
//import ru.besttours.tour.models.User;
//import ru.besttours.tour.repo.PackageTourBidRepository;
//import ru.besttours.tour.repo.PackageTourRepository;
//import ru.besttours.tour.repo.UserRepository;
//import ru.besttours.tour.services.PackageTourBidService;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class PackageTourBidServiceTest {
//
//    @Mock
//    private PackageTourBidRepository packageTourBidRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private PackageTourRepository packageTourRepository;
//
//    @InjectMocks
//    private PackageTourBidService packageTourBidService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void updatePackageTourStatus() {
//        // Создание тестовые данных
//        int userId = 1;
//        int tourId = 1;
//        boolean status = true;
//        PackageTourBid bid = new PackageTourBid();
//        bid.setStatus(false); // Установка начального значения статуса
//
//        User user = new User();
//        user.setId(userId);
//
//        PackageTour tour = new PackageTour();
//        tour.setId(tourId);
//
//        // Настройка моков и их возвращаемых значений
//        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
//        when(packageTourRepository.findById(tourId)).thenReturn(java.util.Optional.of(tour));
//        when(packageTourBidRepository.findById(any())).thenReturn(java.util.Optional.of(bid));
//        when(packageTourBidRepository.save(any())).thenReturn(bid);
//
//        // Вызов тестируемого метода
//        packageTourBidService.updatePackageTourStatus(tourId, userId, status);
//
//        // Проверка, что метод findById был вызван ровно один раз с аргументом типа PackageTourUserId
//        verify(packageTourBidRepository, times(1)).findById(any(PackageTourUserId.class));
//
//        // Проверка, что вызван метод save ровно один раз
//        verify(packageTourBidRepository, times(1)).save(any());
//
//        // Проверка, что статус заявки был изменен на ожидаемое значение
//        assert bid.isStatus() == status;
//    }
//}
