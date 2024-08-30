package ru.besttours.tour.dto;

public class CreatePackageTourBidDTO {

    private int userId;

    private int tourId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }
}
