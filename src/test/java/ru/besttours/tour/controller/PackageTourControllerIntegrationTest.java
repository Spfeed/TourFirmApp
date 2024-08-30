package ru.besttours.tour.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.besttours.tour.dto.SearchTourDTO;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PackageTourControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test //проверка метода getToursForModal() метод возвращает найденные туры по запросу. Ожидается 1 тур
    public void testGetToursForModal() throws Exception {
        SearchTourDTO searchTourDTO = new SearchTourDTO();
        searchTourDTO.setFromId(2);
        searchTourDTO.setToId(1);
        searchTourDTO.setStartDate(new Date());
        searchTourDTO.setDays(12);
        searchTourDTO.setAdults(2);
        searchTourDTO.setChildren(1);

        mockMvc.perform(post("/packagetours/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(searchTourDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test //Проерка метода getActualTourForCity() выдает актуальный тур для города, ожидается "Брест"
    public void testGetActualTourForCityMoscow() throws Exception {
        mockMvc.perform(get("/packagetours/Москва/actual")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cityName", is("Брест")));
    }

    @Test //Проверка метода getActualTourForCity() выдает актуальный тур для города, ожидается "Гомель"
    public void testGetActualTourForCitySamara() throws Exception {
        mockMvc.perform(get("/packagetours/Самара/actual")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cityName", is("Гомель")));
    }

    @Test //Проверка метода getActualTourForCity() выдает актуальный тур для города, ожидается "Варадеро"
    public void testGetActualTourForCitySaintPetersburg() throws Exception {
        mockMvc.perform(get("/packagetours/Санкт-Петербург/actual")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cityName", is("Варадеро")));
    }

    // Helper method to convert objects to JSON string
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
