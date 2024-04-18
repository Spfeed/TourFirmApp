package ru.besttours.tour.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
@Table(name = "package_tour_user")
public class PackageTourUserBid {

    @EmbeddedId
    private PackageTourUserId id;

    @Column(name = "status")
    @NotNull
    private boolean status;

    @Column(name = "created_at")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public PackageTourUserBid() {}

    public PackageTourUserBid(PackageTourUserId id, boolean status, Date createdAt) {
        this.id = id;
        this.status = status;
        this.createdAt = createdAt;
    }

    public PackageTourUserId getId() {
        return id;
    }

    public void setId(PackageTourUserId id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
