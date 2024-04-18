package ru.besttours.tour.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "transfer")
public class Transfer {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotNull(message = "Название вида транспорта не может быть пустым!")
    @Size(min = 3, max = 30, message = "Название вида транспорта должно быть от 3 до 30 символов длиной!")
    private String name;

    @Column(name = "company")
    @NotNull(message = "Название компании-перевозчика не должно быть пустым!")
    @Size(min = 3, max = 100, message = "Название компании-перевозчика должно быть от 3 до 100 символов длиной!")
    private String company;

    @OneToMany(mappedBy = "transfer", cascade = CascadeType.REMOVE)
    private List<PackageTour> packageTours;

    public Transfer() {}

    public Transfer(String name, String company) {
        this.name = name;
        this.company = company;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<PackageTour> getPackageTours() {
        return packageTours;
    }

    public void setPackageTours(List<PackageTour> packageTours) {
        this.packageTours = packageTours;
    }
}
