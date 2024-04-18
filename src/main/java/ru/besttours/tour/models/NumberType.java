package ru.besttours.tour.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "number_type")
public class NumberType {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotNull(message = "Тип номера не может быть пустым!")
    @Size(min = 3, max = 50, message = "Тип номера должен быть от 3 до 50 символов длиной!")
    private String name;

    @OneToMany(mappedBy = "numberType", cascade = CascadeType.REMOVE)
    private List<Number> numbers;

    public NumberType() {}

    public NumberType(String name) {
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

    public List<Number> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Number> numbers) {
        this.numbers = numbers;
    }
}
