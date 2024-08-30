//package ru.besttours.tour.controller;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.modelmapper.ModelMapper;
//import ru.besttours.tour.dto.TourStatusChangeDTO;
//import ru.besttours.tour.services.DynamicTourBidService;
//import ru.besttours.tour.services.PackageTourBidService;
//import ru.besttours.tour.services.SecurityService;
//import ru.besttours.tour.services.UserService;
//
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.times;
//
//public class UserControllerTest {
//
//    @InjectMocks
//    private UserController userController;
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private ModelMapper modelMapper;
//
//    @Mock
//    private SecurityService securityService;
//
//    @Mock
//    private PackageTourBidService packageTourBidService;
//
//    @Mock
//    private DynamicTourBidService dynamicTourBidService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testChangeTourBidStatusDynamic() {
//        // Создание тестовых данных
//        TourStatusChangeDTO dto = new TourStatusChangeDTO();
//        dto.setTourId(1);
//        dto.setUserId(1);
//        dto.setStatus(true);
//        dto.setDynamic(true);
//
//        // Вызов метода
//        userController.changeTourBidStatus(dto);
//
//        // Проверка
//        verify(dynamicTourBidService, times(1)).updatePackageTourStatus(1, 1, true);
//    }
//
//    @Test
//    public void testChangeTourBidStatusPackage() {
//        // Создание тестовых данных
//        TourStatusChangeDTO dto = new TourStatusChangeDTO();
//        dto.setTourId(1);
//        dto.setUserId(1);
//        dto.setStatus(true);
//        dto.setDynamic(false);
//
//        // Вызов метода
//        userController.changeTourBidStatus(dto);
//
//        // Проверка
//        verify(packageTourBidService, times(1)).updatePackageTourStatus(1, 1, true);
//    }
//}
