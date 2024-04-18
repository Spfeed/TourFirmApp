package ru.besttours.tour.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "food_type")
public class FoodType {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotNull(message = "Тип питания не может быть пустым!")
    @Size(min = 2, max = 50, message = "Наименование типа питания должно быть от 3 до 50 символов длиной!")
    private String name;

    @OneToMany(mappedBy = "foodType", cascade = CascadeType.REMOVE)
    private List<DynamicTour> dynamicTours;

    @OneToMany(mappedBy = "foodType", cascade = CascadeType.REMOVE)
    private List<PackageTour> packageTours;

    public FoodType() {}

    public FoodType(String name) {
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
