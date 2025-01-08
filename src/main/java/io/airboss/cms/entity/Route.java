package io.airboss.cms.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "routes") // Mapea la tabla 'routes'
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autoincremental
    private Long id;
    
    @ManyToOne // Relación con la tabla 'airports' (origen)
    @JoinColumn(name = "AeropuertoOrigenID", nullable = false)
    private Airport originAirport;
    
    @ManyToOne // Relación con la tabla 'airports' (destino)
    @JoinColumn(name = "AeropuertoDestinoID", nullable = false)
    private Airport destinationAirport;
    
    @Column(name = "DuracionEstimada", nullable = false)
    private Integer estimatedDuration;
    
    @Column(name = "Frecuencia", nullable = true)
    private String frequency;
    
    @Column(name = "Activo", nullable = false)
    private Boolean active;
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Airport getOriginAirport() {
        return originAirport;
    }
    
    public void setOriginAirport(Airport originAirport) {
        this.originAirport = originAirport;
    }
    
    public Airport getDestinationAirport() {
        return destinationAirport;
    }
    
    public void setDestinationAirport(Airport destinationAirport) {
        this.destinationAirport = destinationAirport;
    }
    
    public Integer getEstimatedDuration() {
        return estimatedDuration;
    }
    
    public void setEstimatedDuration(Integer estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }
    
    public String getFrequency() {
        return frequency;
    }
    
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    
    public Boolean getActive() {
        return active;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    }
}
