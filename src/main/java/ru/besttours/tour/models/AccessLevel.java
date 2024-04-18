package ru.besttours.tour.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "access_level")
public class AccessLevel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotNull(message = "Название роли не должно быть пустым!")
    @Size(min = 5, max = 50, message = "Название роли должно быть от 5 до 50 символов длиной!")
    private String name;

    @OneToMany(mappedBy = "accessLevel", cascade = CascadeType.REMOVE)
    private List<User> users;

    public  AccessLevel() {}

    public AccessLevel(String name) {
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
