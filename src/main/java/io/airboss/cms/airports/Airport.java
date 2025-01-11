package io.airboss.cms.airports;


import jakarta.persistence.*;

@Entity
@Table(name = "airports")
public class Airport {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airport_id")
    private Long airportId;
    
    
    private String iataCode;
    private String name;
    private String countryIsoCode;
    
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
    
    public Long getAirportId() {
        return airportId;
    }
    
    public void setAirportId(Long airportId) {
        this.airportId = airportId;
    }
}