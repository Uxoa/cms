package io.airboss.cms.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "airports")
public class Airport {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "NombreAeropuerto", nullable = false)
    private String airportName;
    
    @Column(name = "Codigo", unique = true, nullable = false) // Código único
    private String code;
    
    @Column(name = "Ciudad", nullable = false)
    private String city;
    
    @Column(name = "Pais", nullable = false)
    private String country;
    
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
}
