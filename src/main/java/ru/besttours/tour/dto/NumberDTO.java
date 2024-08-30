package ru.besttours.tour.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

public class NumberDTO {

    private int hotelId;

    private int numberTypeId;

    private String description;

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getNumberTypeId() {
        return numberTypeId;
    }

    public void setNumberTypeId(int numberTypeId) {
        this.numberTypeId = numberTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
