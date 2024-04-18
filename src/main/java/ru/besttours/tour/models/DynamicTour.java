package ru.besttours.tour.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "dynamic_tour")
public class DynamicTour {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "duration")
    @Min(value = 3, message = "Длительность тура не может быть меньше 3 дней!")
    @Max(value = 100, message = "Длительность тура не может быть более 100 дней!")
    private int duration;

    @Column(name = "num_adults")
    @Min(value = 1, message = "Количество взрослых не может быть менее 1!")
    @Max(value = 5, message = "Количество взрослых не может быть больше 5!")
    private int numAdults;

    @Column(name = "num_children")
    @Min(value = 0, message = "Количество детей не может быть меньше 0!")
    @Max(value = 4, message = "Количество детей не может быть более 4!")
    private int numChildren;

    @Column(name = "description")
    @NotNull(message = "Поле пожеланий обязательно к заполнению!")
    private String description;

    @Column(name = "date_start")
    @NotNull(message = "Дата начала не может быть пустой!")
    @Temporal(TemporalType.DATE)
    private Date dateStart;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "number_id", referencedColumnName = "id")
    private Number number;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "begin_place", referencedColumnName = "id")
    private City beginPlace;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "end_place", referencedColumnName = "id")
    private City endPlace;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "food_type_id", referencedColumnName = "id")
    private FoodType foodType;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "dynamic_tour_service",
            joinColumns = @JoinColumn(name = "dynamic_tour_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<Service> services;

    public DynamicTour() {}

    public DynamicTour(int duration, int numAdults, int numChildren, String description, Date dateStart,
                       Number number, City beginPlace, City endPlace, FoodType foodType) {
        this.duration = duration;
        this.numAdults = numAdults;
        this.numChildren = numChildren;
        this.description = description;
        this.dateStart = dateStart;
        this.number = number;
        this.beginPlace = beginPlace;
        this.endPlace = endPlace;
        this.foodType = foodType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Number getNumber() {
        return number;
    }

    public void setNumber(Number number) {
        this.number = number;
    }

    public City getBeginPlace() {
        return beginPlace;
    }

    public void setBeginPlace(City beginPlace) {
        this.beginPlace = beginPlace;
    }

    public City getEndPlace() {
        return endPlace;
    }

    public void setEndPlace(City endPlace) {
        this.endPlace = endPlace;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }

    public Set<Service> getServices() {
        return services;
    }

    public void setServices(Set<Service> services) {
        this.services = services;
    }
}
