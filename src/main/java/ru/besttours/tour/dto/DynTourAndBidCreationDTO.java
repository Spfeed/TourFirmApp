package ru.besttours.tour.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;

public class DynTourAndBidCreationDTO {
    private int userId;

    private int cityStart;

    private int cityEnd;

    private int numAdults;

    private int numChildren;

    private int numberId;

    private int hotelId;

    private int foodTypeId;
    @Temporal(TemporalType.DATE)
    private Date dateStart;

    private String description;

    private int duration;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCityStart() {
        return cityStart;
    }

    public void setCityStart(int cityStart) {
        this.cityStart = cityStart;
    }

    public int getCityEnd() {
        return cityEnd;
    }

    public void setCityEnd(int cityEnd) {
        this.cityEnd = cityEnd;
    }

    public int getNumAdults() {
        return numAdults;
    }

    public void setNumAdults(int numAdults) {
        this.numAdults = numAdults;
    }

    public int getNumChildren() {
        return numChildren;
    }

    public void setNumChildren(int numChildren) {
        this.numChildren = numChildren;
    }

    public int getNumberId() {
        return numberId;
    }

    public void setNumberId(int numberId) {
        this.numberId = numberId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFoodTypeId() {
        return foodTypeId;
    }

    public void setFoodTypeId(int foodTypeId) {
        this.foodTypeId = foodTypeId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
