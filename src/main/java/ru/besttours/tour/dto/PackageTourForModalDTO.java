package ru.besttours.tour.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.math.BigDecimal;
import java.util.Date;

public class PackageTourForModalDTO {
    private int id;

    private String name;

    private String cityName;

    private String hotelName;

    private BigDecimal hotelRating;

    private BigDecimal costPack;

    private String cityPhoto;

    @Temporal(TemporalType.DATE)
    private Date dateStart;

    private int duration;

    private int foodId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public BigDecimal getHotelRating() {
        return hotelRating;
    }

    public void setHotelRating(BigDecimal hotelRating) {
        this.hotelRating = hotelRating;
    }

    public BigDecimal getCostPack() {
        return costPack;
    }

    public void setCostPack(BigDecimal costPack) {
        this.costPack = costPack;
    }

    public String getCityPhoto() {
        return cityPhoto;
    }

    public void setCityPhoto(String cityPhoto) {
        this.cityPhoto = cityPhoto;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }


}
