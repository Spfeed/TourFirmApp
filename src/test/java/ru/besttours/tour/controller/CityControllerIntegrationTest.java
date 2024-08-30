package ru.besttours.tour.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CityControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test //проверка метода getCitiesForSearchForm список городов для формы поиска пакетного тура
    public void testGetCitiesForSearchForm() throws Exception {
        mockMvc.perform(get("/cities/search-form")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //статус ответа 200
                .andExpect(jsonPath("$", hasSize(greaterThan(0)))); //ответ не пуст
    }
}
