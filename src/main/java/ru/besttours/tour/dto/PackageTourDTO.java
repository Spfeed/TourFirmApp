package ru.besttours.tour.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Date;

public class PackageTourDTO {

    private int startCityId;

    private int endCityId;

    private int numberId;

    private int foodTypeId;

    private int transferId;

    private int touroperatorId;


    private String name;

    @Temporal(TemporalType.DATE)
    private Date dateStart;

    private int duration;

    private int numAdults;

    private int numChildren;

    private String description;

    private BigDecimal costPack;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCostPack() {
        return costPack;
    }

    public void setCostPack(BigDecimal costPack) {
        this.costPack = costPack;
    }

    public int getStartCityId() {
        return startCityId;
    }

    public void setStartCityId(int startCityId) {
        this.startCityId = startCityId;
    }

    public int getEndCityId() {
        return endCityId;
    }

    public void setEndCityId(int endCityId) {
        this.endCityId = endCityId;
    }

    public int getNumberId() {
        return numberId;
    }

    public void setNumberId(int numberId) {
        this.numberId = numberId;
    }

    public int getFoodTypeId() {
        return foodTypeId;
    }

    public void setFoodTypeId(int foodTypeId) {
        this.foodTypeId = foodTypeId;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getTouroperatorId() {
        return touroperatorId;
    }

    public void setTouroperatorId(int touroperatorId) {
        this.touroperatorId = touroperatorId;
    }
}
