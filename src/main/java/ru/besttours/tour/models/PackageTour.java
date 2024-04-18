package ru.besttours.tour.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "package_tour")
public class PackageTour {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotNull(message = "Название тура не может быть пустым!")
    @Size(min = 5, max = 100, message = "Название тура должно быть от 5 до 100 символов длиной!")
    private String name;

    @Column(name = "date_start")
    @NotNull(message = "Дата отправления не должна быть пустой!")
    @Temporal(TemporalType.DATE)
    private Date dateStart;

    @Column(name = "duration")
    @NotNull(message = "Длительность тура не должна быть пустой!")
    @Min(value = 3, message = "Минимальная длительность тура 3 дня!")
    @Max(value = 100, message = "Максимальная длительность тура должна быть до 100 дней!")
    private int duration;

    @Column(name = "num_adults")
    @Min(value = 1,message = "Количество взрослых не может быть менее 1!")
    @Max(value = 5, message = "Количество взрослых не может быть больше 5!")
    private int numAdults;

    @Column(name = "num_children")
    @Min(value = 0, message = "Количество детей не должно быть менее 0!")
    @Max(value = 4, message = "Количество детей не должно быть более 4!")
    private int numChildren;

    @Column(name = "description")
    @NotNull(message = "Описание тура не должно быть пустым!")
    private String description;

    @Column(name = "cost_pack")
    @NotNull(message = "Стоимость тура не может быть пустой!")
    private BigDecimal costPack;

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

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "tour_operator_id", referencedColumnName = "id")
    private TourOperator tourOperator;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "transfer_id", referencedColumnName = "id")
    private Transfer transfer;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "package_tour_service",
            joinColumns = @JoinColumn(name = "package_tour_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<Service> services;

    public PackageTour() {}

    public PackageTour(String name, Date dateStart, int duration, int numAdults, int numChildren,
                       String description, BigDecimal costPack, Number number, City beginPlace,
                       City endPlace, FoodType foodType, TourOperator tourOperator, Transfer transfer) {
        this.name = name;
        this.dateStart = dateStart;
        this.duration = duration;
        this.numAdults = numAdults;
        this.numChildren = numChildren;
        this.description = description;
        this.costPack = costPack;
        this.number = number;
        this.beginPlace = beginPlace;
        this.endPlace = endPlace;
        this.foodType = foodType;
        this.tourOperator = tourOperator;
        this.transfer = transfer;
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

    public TourOperator getTourOperator() {
        return tourOperator;
    }

    public void setTourOperator(TourOperator tourOperator) {
        this.tourOperator = tourOperator;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }

    public Set<Service> getServices() {
        return services;
    }

    public void setServices(Set<Service> services) {
        this.services = services;
    }
}
