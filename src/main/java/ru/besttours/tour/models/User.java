package ru.besttours.tour.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "user_tour")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotNull(message = "Имя не должно быть пустым!")
    @Size(min = 2, max = 150, message = "Имя должно быть от 2 до 150 символов длиной!")
    private String name;

    @Column(name = "last_name")
    @NotNull(message = "Фамилия не должна быть пустой!")
    @Size(min = 1, max = 150, message = "Фамилия должна быть от 1 до 100 символов длиной!")
    private String lastName;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "email")
    @NotNull(message = "Электронная почта не должна быть пустой!")
    @Email(message = "Некорректная электронная почта!")
    private String email;

    @Column(name = "phone_number")
    @NotNull(message = "Номер телефона не может быть пустым!")
    @Size(min=11, max = 12, message = "Номер телефона должен состоять из 11 символов!")
    private String phoneNumber;

    @Column(name = "password")
    @NotNull(message = "Пароль не должен быть пустым!")
    @Size(min = 10, message = "Пароль должен быть минимум 10 символов длиной!")
    private String password;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "access_level_id", referencedColumnName = "id")
    private AccessLevel accessLevel;

    public User() {}

    public User(String name, String lastName, String fatherName, String email,
                String phoneNumber, String password, AccessLevel accessLevel) {
        this.name = name;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.accessLevel = accessLevel;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }
}
