package ru.besttours.tour.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "country")
public class Country {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotNull(message = "Название страны не может быть пустым!")
    @Size(max = 100, message = "Название страны не может превышать 100 символов!")
    private String name;

    @Column(name = "description")
    @NotNull(message = "Описание страны не может быть пустым!")
    private String description;

    @Column(name = "visa")
    @NotNull(message = "Ну как boolean может быть null?")
    private boolean visa;

    @Column(name = "language")
    @NotNull(message = "Язык не может быть пустым!")
    @Size(min = 2, max = 50, message = "Название языка должно быть в пределе от 2 до 50 символов!")
    private String language;

    @Column(name = "currency")
    @NotNull(message = "Валюта обязательна для заполнения!")
    @Size(min = 2, max = 50, message = "Название валюты должно быть от 2 до 50 символов длиной!")
    private String currency;

    @Column(name = "local_time")
    @NotNull(message = "Часовой пояс обязателен для заполнения!")
    @Size(min = 5, max = 50, message = "Часовой пояс должен выглядеть так: UTC+8, при желании можно добавить города!")
    private String localTime;

    @Column(name = "religion")
    @NotNull(message = "Религии страны обязательны для заполнения!")
    @Size(min = 3 ,max = 100, message = "Не более 100 символов!")
    private String religion;

    //данная конструкция нужна для получения доступа к фото страны через отношение country_photo
    @ManyToMany
    @JoinTable(
            name = "country_photo", //промежуточная таблица
            joinColumns = @JoinColumn(name = "country_id"), //внешний ключ для страны
            inverseJoinColumns = @JoinColumn(name = "photos_id") //внешний ключ для фото
    )
    private Set<Photo> photos; //set, так как нужны уникальные объекты

    @OneToMany(mappedBy = "country", cascade = CascadeType.REMOVE)
    private List<City> cities;



    public Country() {
        this.photos = new HashSet<>(); //создаем множество для будущего добавления фотографий
    }

    public Country(String name, String description, boolean visa, String language, String currency,
                   String localTime, String religion) {
        this.name = name;
        this.description = description;
        this.visa = visa;
        this.language = language;
        this.currency = currency;
        this.localTime = localTime;
        this.religion = religion;
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

    public boolean isVisa() {
        return visa;
    }

    public void setVisa(boolean visa) {
        this.visa = visa;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLocalTime() {
        return localTime;
    }

    public void setLocalTime(String localTime) {
        this.localTime = localTime;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
