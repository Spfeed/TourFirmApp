package ru.besttours.tour.dto;
import ru.besttours.tour.models.City;
import java.math.BigDecimal;

public class HotelDTO {

    private String name;

    private BigDecimal rating;

    private int beachLine;

    private String information;

    private String services;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public int getBeachLine() {
        return beachLine;
    }

    public void setBeachLine(int beachLine) {
        this.beachLine = beachLine;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }
}
