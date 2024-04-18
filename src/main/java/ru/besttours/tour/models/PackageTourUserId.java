package ru.besttours.tour.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PackageTourUserId {

    @Column(name = "user_id")
    private int userId;

    @Column(name = "package_tour_id")
    private int packageTourId;

    public PackageTourUserId() {}

    public PackageTourUserId(int userId, int packageTourId) {
        this.userId = userId;
        this.packageTourId = packageTourId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPackageTourId() {
        return packageTourId;
    }

    public void setPackageTourId(int packageTourId) {
        this.packageTourId = packageTourId;
    }
}
