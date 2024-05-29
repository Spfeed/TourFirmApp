package ru.besttours.tour.dto;

import java.util.List;

public class CountryForCountryPageDTO {
    private String name;
    private String description;
    private String countryPhoto;
    private List<String> cityPhotos;

    private boolean visa;

    private String language;

    private String currency;

    private String localTime;

    private String religion;

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

    // Getters and setters
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

    public String getCountryPhoto() {
        return countryPhoto;
    }

    public void setCountryPhoto(String countryPhoto) {
        this.countryPhoto = countryPhoto;
    }

    public List<String> getCityPhotos() {
        return cityPhotos;
    }

    public void setCityPhotos(List<String> cityPhotos) {
        this.cityPhotos = cityPhotos;
    }
}
