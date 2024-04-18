package ru.besttours.tour.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "city")
public class City {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotNull(message = "Название города не должно быть пустым!")
    @Size(max = 200, message = "Города с названием более 200 символов не существует!")
    private String name;

    @Column(name = "description")
    @NotNull(message = "Описание города обязательно к заполнению!")
    private String description;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    @ManyToMany
    @JoinTable(
            name = "city_photo",
            joinColumns = @JoinColumn(name = "city_id"),
            inverseJoinColumns = @JoinColumn(name = "photo_id")
    )
    private Set<Photo> photos;

    @OneToMany(mappedBy = "city", cascade = CascadeType.REMOVE)
    private List<Hotel> hotels;

    @OneToMany(mappedBy = "beginPlace", cascade = CascadeType.REMOVE)
    private List<DynamicTour> beginPlaceTours;

    @OneToMany(mappedBy = "endPlace", cascade = CascadeType.REMOVE)
    private List<DynamicTour> endPlaceTours;

    @OneToMany(mappedBy = "beginPlace", cascade = CascadeType.REMOVE)
    private List<PackageTour> beginPlacePackageTours;

    @OneToMany(mappedBy = "endPlace", cascade = CascadeType.REMOVE)
    private List<PackageTour> endPlacePackageTours;

    public City() {
        this.photos = new HashSet<>();
    }

    public City(String name, String description, Country country) {
        this.name = name;
        this.description = description;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }

    public List<DynamicTour> getBeginPlaceTours() {
        return beginPlaceTours;
    }

    public void setBeginPlaceTours(List<DynamicTour> beginPlaceTours) {
        this.beginPlaceTours = beginPlaceTours;
    }

    public List<DynamicTour> getEndPlaceTours() {
        return endPlaceTours;
    }

    public void setEndPlaceTours(List<DynamicTour> endPlaceTours) {
        this.endPlaceTours = endPlaceTours;
    }

    public List<PackageTour> getBeginPlacePackageTours() {
        return beginPlacePackageTours;
    }

    public void setBeginPlacePackageTours(List<PackageTour> beginPlacePackageTours) {
        this.beginPlacePackageTours = beginPlacePackageTours;
    }

    public List<PackageTour> getEndPlacePackageTours() {
        return endPlacePackageTours;
    }

    public void setEndPlacePackageTours(List<PackageTour> endPlacePackageTours) {
        this.endPlacePackageTours = endPlacePackageTours;
    }
}
