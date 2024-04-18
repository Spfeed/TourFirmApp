package ru.besttours.tour.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "service")
public class Service {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotNull(message = "Наименование услуги не должно быть пустым!")
    @Size(min = 3, max = 150, message = "Наименование услуги должно быть от 3 до 150 символов длиной!")
    private String name;

    @ManyToMany(mappedBy = "services")
    private Set<DynamicTour> dynamicTours;

    @ManyToMany(mappedBy = "services")
    private Set<PackageTour> packageTours;

    public Service() {}

    public Service(String name) {
        this.name = name;
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

    public Set<DynamicTour> getDynamicTours() {
        return dynamicTours;
    }

    public void setDynamicTours(Set<DynamicTour> dynamicTours) {
        this.dynamicTours = dynamicTours;
    }

    public Set<PackageTour> getPackageTours() {
        return packageTours;
    }

    public void setPackageTours(Set<PackageTour> packageTours) {
        this.packageTours = packageTours;
    }
}
