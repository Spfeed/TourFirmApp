package ru.besttours.tour.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class DynamicTourUserId implements Serializable {
    @Column(name = "user_id")
    private int userId;

    @Column(name = "dynamic_tour_id")
    private int dynamicTourId;

    public DynamicTourUserId() {}

    public DynamicTourUserId(int userId, int dynamicTourId) {
        this.userId = userId;
        this.dynamicTourId = dynamicTourId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDynamicTourId() {
        return dynamicTourId;
    }

    public void setDynamicTourId(int dynamicTourId) {
        this.dynamicTourId = dynamicTourId;
    }
}
