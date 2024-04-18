package ru.besttours.tour.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tour_operator")
public class TourOperator {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotNull(message = "Название туроператора не может быть пустым!")
    @Size(min = 3, max = 70, message = "Название туроператора должно быть от 3 до 70 символов длиной!")
    private String name;

    @Column(name = "description")
    @NotNull(message = "Описание туроператора не должно быть пустым!")
    private String description;

    @Column(name = "site")
    @NotNull(message = "Ссылка на сайт туроператора не может быть пустой!")
    private String site;

    @Column(name = "rating")
    @NotNull(message = "Рейтинг туроператора не может быть пустым!")
    private double rating;

    @ManyToMany
    @JoinTable(
            name = "tour_operator_photo",
            joinColumns = @JoinColumn(name = "tour_operator_id"),
            inverseJoinColumns = @JoinColumn(name = "photo_id")
    )
    private Set<Photo> photos;

    @OneToMany(mappedBy = "tourOperator", cascade = CascadeType.REMOVE)
    private List<PackageTour> packageTours;

    public TourOperator() {
        this.photos = new HashSet<>();
    }

    public TourOperator(String name, String description, String site, double rating) {
        this.name = name;
        this.description = description;
        this.site = site;
        this.rating = rating;
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

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public List<PackageTour> getPackageTours() {
        return packageTours;
    }

    public void setPackageTours(List<PackageTour> packageTours) {
        this.packageTours = packageTours;
    }
}
