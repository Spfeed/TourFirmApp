package ru.besttours.tour.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.besttours.tour.dto.UserDTO;
import ru.besttours.tour.dto.UserLogInDTO;
import ru.besttours.tour.dto.UserDTOForGlobalState;

import jakarta.servlet.http.HttpSession;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HttpSession httpSession;

    @Test //Проверка метода signIn() - вход в учетную запись
    public void testSignIn() throws Exception {
        // Создание тестовых данных
        UserLogInDTO userLogInDTO = new UserLogInDTO();
        userLogInDTO.setEmail("test@mail.ru");
        userLogInDTO.setPassword("Testtest123");

        // Отправка POST-запроса и проверка ответа
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userLogInDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(userLogInDTO.getEmail())))
                .andExpect(jsonPath("$.password").doesNotExist()); // Не должен возвращаться пароль в ответе
    }

    @Test //Проверка метода signIn() - вход в учетную запись
    public void testNegativeSignIn() throws Exception {
        // Создание тестовых данных
        UserLogInDTO userLogInDTO = new UserLogInDTO();
        userLogInDTO.setEmail("0@mail.ru");
        userLogInDTO.setPassword("abc");

        // Отправка POST-запроса и проверка ответа
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userLogInDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test // Проверка метода signUp() - регистрация новой учетной записи
    public void testSignUp() throws Exception {
        // Создание тестовых данных для регистрации
        UserDTO newUser = new UserDTO();
        newUser.setEmail("newuser2@example.com");
        newUser.setPassword("NewUser1234567890");
        newUser.setName("user2");
        newUser.setLastName("user2");
        newUser.setFatherName("user2");
        newUser.setPhoneNumber("11111111112");


        // Отправка POST-запроса на регистрацию и проверка ответа
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", is(newUser.getEmail())))
                .andExpect(jsonPath("$.password").doesNotExist()); // Не должен возвращаться пароль в ответе
    }
    @Test // Проверка метода signUp() - регистрация новой учетной записи
    public void NegativetestSignUp() throws Exception {
        // Создание тестовых данных для регистрации
        UserDTO newUser = new UserDTO();
        newUser.setEmail("newuser@example.com");
        newUser.setPassword("NewUser"); //Пароль должен содержать цифры
        newUser.setName("user");
        newUser.setLastName("user");
        newUser.setFatherName("");
        newUser.setPhoneNumber("111"); //Номер телефона должен состоять из 11 символов


        // Отправка POST-запроса на регистрацию и проверка ответа
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newUser)))
                .andExpect(status().isBadRequest());
    }

    // Метод для преобразования объекта в JSON строку
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
