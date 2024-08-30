package ru.besttours.tour.dto;

public class NumberForCrudDTO {
    private int id;
    private String hotelName;

    private String numberTypeName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getNumberTypeName() {
        return numberTypeName;
    }

    public void setNumberTypeName(String numberTypeName) {
        this.numberTypeName = numberTypeName;
    }
}
