package ru.besttours.tour.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDTO {
    @NotNull(message = "Имя не должно быть пустым!")
    @Size(min = 2, max = 150, message = "Имя должно быть от 2 до 150 символов длиной!")
    private String name;

    @NotNull(message = "Фамилия не должна быть пустой!")
    @Size(min = 1, max = 150, message = "Фамилия должна быть от 1 до 100 символов длиной!")
    private String lastName;

    private String fatherName;

    @NotNull(message = "Электронная почта не должна быть пустой!")
    @Email(message = "Некорректная электронная почта!")
    private String email;

    @NotNull(message = "Номер телефона не может быть пустым!")
    @Size(min=11, max = 12, message = "Номер телефона должен состоять из 11 символов!")
    private String phoneNumber;

    @NotNull(message = "Пароль не должен быть пустым!")
    @Size(min = 10, message = "Пароль должен быть минимум 10 символов длиной!")
    private String password;


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
}
