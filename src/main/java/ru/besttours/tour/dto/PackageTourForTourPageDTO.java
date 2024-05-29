package ru.besttours.tour.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PackageTourForTourPageDTO {
    private int id;

    private String cityFrom;

    @Temporal(TemporalType.DATE)
    private Date dateStart;

    private String cityTo;

    private String countryTo;

    private int duration;

    private int numAdults;

    private int numChildren;

    private List<String> hotelPhotos;

    private String hotelName;

    private BigDecimal hotelRating;

    private String numberName;

    private String foodType;

    private int beachLine;

    private String hotelInfo;

    private String hotelService;

    private List<String> tourServices;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityFrom() {
        return cityFrom;
    }

    public void setCityFrom(String cityFrom) {
        this.cityFrom = cityFrom;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public String getCityTo() {
        return cityTo;
    }

    public void setCityTo(String cityTo) {
        this.cityTo = cityTo;
    }

    public String getCountryTo() {
        return countryTo;
    }

    public void setCountryTo(String countryTo) {
        this.countryTo = countryTo;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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

    public List<String> getHotelPhotos() {
        return hotelPhotos;
    }

    public void setHotelPhotos(List<String> hotelPhotos) {
        this.hotelPhotos = hotelPhotos;
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

    public String getNumberName() {
        return numberName;
    }

    public void setNumberName(String numberName) {
        this.numberName = numberName;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public int getBeachLine() {
        return beachLine;
    }

    public void setBeachLine(int beachLine) {
        this.beachLine = beachLine;
    }

    public String getHotelInfo() {
        return hotelInfo;
    }

    public void setHotelInfo(String hotelInfo) {
        this.hotelInfo = hotelInfo;
    }

    public String getHotelService() {
        return hotelService;
    }

    public void setHotelService(String hotelService) {
        this.hotelService = hotelService;
    }

    public List<String> getTourServices() {
        return tourServices;
    }

    public void setTourServices(List<String> tourServices) {
        this.tourServices = tourServices;
    }
}
