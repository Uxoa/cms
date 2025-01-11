package io.airboss.cms.airports;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Airport {
    
 
    private String iataCode;
    private String name;
    private String countryIsoCode;
    @Id
    @GeneratedValue
    private Long id;
    
    public Airport() {
        super();
    }
    
    public Airport(String iataCode, String name, String countryIsoCode) {
        super();
        this.iataCode = iataCode;
        this.name = name;
        this.countryIsoCode = countryIsoCode;
    }
    
    public String getIataCode() {
        return iataCode;
    }
    
    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCountryIsoCode() {
        return countryIsoCode;
    }
    
    public void setCountryIsoCode(String countryIsoCode) {
        this.countryIsoCode = countryIsoCode;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return id;
    }
}