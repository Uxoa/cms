package io.airboss.cms.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "airports")
public class Airport {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "NombreAeropuerto", nullable = false)
    private String airportName;
    
    @Column(name = "Codigo", unique = true, nullable = false)
    private String code;
    
    @Column(name = "Ciudad", nullable = false)
    private String city;
    
    @Column(name = "Pais", nullable = false)
    private String country;
    
    // Constructor vacío
    public Airport() {}
    
    // Constructor con parámetros
    public Airport(Long id, String airportName, String code, String city, String country) {
        this.id = id;
        this.airportName = airportName;
        this.code = code;
        this.city = city;
        this.country = country;
    }
    
    // Getters y setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getAirportName() {
        return airportName;
    }
    
    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    @Override
    public String toString() {
        return "Airport{" +
              "id=" + id +
              ", airportName='" + airportName + '\'' +
              ", code='" + code + '\'' +
              ", city='" + city + '\'' +
              ", country='" + country + '\'' +
              '}';
    }
}
