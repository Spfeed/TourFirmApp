package ru.besttours.tour.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class CountryDTO {

    @NotNull(message = "Название страны не может быть пустым!")
    @Size(max = 100, message = "Название страны не может превышать 100 символов!")
    private String name;

    @NotNull(message = "Описание страны не может быть пустым!")
    private String description;

    @NotNull(message = "Ну как boolean может быть null?")
    private boolean visa;

    @NotNull(message = "Язык не может быть пустым!")
    @Size(min = 2, max = 50, message = "Название языка должно быть в пределе от 2 до 50 символов!")
    private String language;

    @NotNull(message = "Валюта обязательна для заполнения!")
    @Size(min = 2, max = 50, message = "Название валюты должно быть от 2 до 50 символов длиной!")
    private String currency;

    @NotNull(message = "Часовой пояс обязателен для заполнения!")
    @Size(min = 5, max = 50, message = "Часовой пояс должен выглядеть так: UTC+8, при желании можно добавить города!")
    private String localTime;

    @NotNull(message = "Религии страны обязательны для заполнения!")
    @Size(min = 3, max = 100, message = "Не более 100 символов!")
    private String religion;

    @NotNull(message = "Фото не может быть пустым!")
    private MultipartFile photo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVisa() {
        return visa;
    }

    public void setVisa(boolean visa) {
        this.visa = visa;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLocalTime() {
        return localTime;
    }

    public void setLocalTime(String localTime) {
        this.localTime = localTime;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }
}
