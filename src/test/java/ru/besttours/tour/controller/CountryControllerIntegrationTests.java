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
public class CountryControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test //проверка метода testGetCountriesNames() для хедера клиетской части (Страны)
    public void testGetCountriesNames() throws Exception {
        mockMvc.perform(get("/countries/names")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //статус ответа должен быть 200
                .andExpect(jsonPath("$", hasSize(greaterThan(0)))); //ответ не пустой
    }

    @Test //проверка метода getAllCountryCities() для хедера клиентской части (Города)
    public void testGetAllCountryCities() throws Exception {
        mockMvc.perform(get("/countries/all-cities/Россия")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())//статус ответа 200
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));//ответ не пустой
    }

    @Test //проверка метода getCountriesForSearchForm() для формы поиска пакетного тура (Страна)
    public void testGetCountriesForSearchForm() throws Exception {
        mockMvc.perform(get("/countries/search-form")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //статус ответа 200
                .andExpect(jsonPath("$", hasSize(greaterThan(0)))); //ответ не пуст
    }
}
