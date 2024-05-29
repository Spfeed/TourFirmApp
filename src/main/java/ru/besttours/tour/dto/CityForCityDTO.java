package ru.besttours.tour.dto;

import java.util.List;

public class CityForCityDTO {

    private String name;

    private String description;

    private String photoBg;

    private List<String> cityPhotos;

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

    public String getPhotoBg() {
        return photoBg;
    }

    public void setPhotoBg(String photoBg) {
        this.photoBg = photoBg;
    }

    public List<String> getCityPhotos() {
        return cityPhotos;
    }

    public void setCityPhotos(List<String> cityPhotos) {
        this.cityPhotos = cityPhotos;
    }
}
