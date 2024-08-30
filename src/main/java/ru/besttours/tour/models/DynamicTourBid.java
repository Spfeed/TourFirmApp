package ru.besttours.tour.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
@Table(name = "dynamic_tour_user")
public class DynamicTourBid {

    @EmbeddedId
    private DynamicTourUserId id;

    @Column(name = "status")
    @NotNull(message = "Стаутс не может быть null!")
    private boolean status;

    @Column(name = "created_at")
    @NotNull(message = "Ну тут автоматически проставляется!")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    public DynamicTourBid() {}

    public DynamicTourBid(DynamicTourUserId id, boolean status, Date created_at) {
        this.id = id;
        this.status = status;
        this.created_at = created_at;
    }

    public DynamicTourUserId getId() {
        return id;
    }

    public void setId(DynamicTourUserId id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
