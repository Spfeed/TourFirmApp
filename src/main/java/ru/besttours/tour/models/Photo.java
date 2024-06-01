    package ru.besttours.tour.models;

    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotNull;

    import java.util.Set;

    @Entity
    @Table(name = "photos")
    public class Photo {

        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column(name = "filepath")
        @NotNull(message = "Путь до фото недолжен быть пустым!")
        private String filePath;

        //для страны
        @ManyToMany(mappedBy = "photos")
        private Set<Country> countries;

        @ManyToMany(mappedBy = "photos")
        private Set<City> cities;

        @ManyToMany(mappedBy = "photos")
        private Set<Hotel> hotels;

        public Photo() {}

        public Photo(String filePath) {
            this.filePath = filePath;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public Set<Country> getCountries() {
            return countries;
        }

        public void setCountries(Set<Country> countries) {
            this.countries = countries;
        }

        public Set<City> getCities() {
            return cities;
        }

        public void setCities(Set<City> cities) {
            this.cities = cities;
        }

        public Set<Hotel> getHotels() {
            return hotels;
        }

        public void setHotels(Set<Hotel> hotels) {
            this.hotels = hotels;
        }
    }
