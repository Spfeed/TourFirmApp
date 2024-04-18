package ru.besttours.tour.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "number")
public class Number {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description")
    @NotNull(message = "Описание номера не может быть пустым!")
    private String description;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "number_type_id", referencedColumnName = "id")
    private NumberType numberType;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    private Hotel hotel;

    @OneToMany(mappedBy = "number", cascade = CascadeType.REMOVE)
    private List<DynamicTour> dynamicTours;

    @OneToMany(mappedBy = "number", cascade = CascadeType.REMOVE)
    private List<PackageTour> packageTours;

    public Number() {}

    public Number(String description, NumberType numberType, Hotel hotel) {
        this.description = description;
        this.numberType = numberType;
        this.hotel = hotel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public NumberType getNumberType() {
        return numberType;
    }

    public void setNumberType(NumberType numberType) {
        this.numberType = numberType;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public List<DynamicTour> getDynamicTours() {
        return dynamicTours;
    }

    public void setDynamicTours(List<DynamicTour> dynamicTours) {
        this.dynamicTours = dynamicTours;
    }

    public List<PackageTour> getPackageTours() {
        return packageTours;
    }

    public void setPackageTours(List<PackageTour> packageTours) {
        this.packageTours = packageTours;
    }
}
