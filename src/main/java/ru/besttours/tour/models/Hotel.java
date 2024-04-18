package ru.besttours.tour.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "hotel")
public class Hotel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotNull(message = "Название отеля не должно быть пустым!")
    @Size(min = 3, max = 100, message = "Название отеля должно быть от 3 до 100 символов длиной!")
    private String name;

    @Column(name = "rating")
    @NotNull(message = "Рейтинг отеля не должен быть пустым!")
    private BigDecimal rating;

    @Column(name = "beach_line")
    @NotNull(message = "Линия пляжа не может быть пустой! Если ее нет поставьте 0")
    private int beachLine;

    @Column(name = "information")
    @NotNull(message = "Информация об отеле не может быть пустой!")
    private String information;

    @Column(name = "services")
    @NotNull(message = "Информация об услугах отеля не может быть пустой!")
    private String services;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @ManyToMany
    @JoinTable(
            name="hotel_photo",
            joinColumns = @JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "photos_id")
    )
    private Set<Photo> photos;

    @OneToMany(mappedBy ="hotel", cascade = CascadeType.REMOVE)
    private List<Number> numbers;

    public Hotel() {
        this.photos = new HashSet<>();
    }

    public Hotel(String name, BigDecimal rating, int beachLine, String information, String services, City city) {
        this.name = name;
        this.rating = rating;
        this.beachLine = beachLine;
        this.information = information;
        this.services = services;
        this.city = city;
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public List<Number> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Number> numbers) {
        this.numbers = numbers;
    }
}
