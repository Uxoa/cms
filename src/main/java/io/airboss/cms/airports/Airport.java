package io.airboss.cms.airports;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "airports")
public class Airport {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airport_id")
    private Long airportId;
    
    @Column(name = "nombre_aeropuerto", nullable = false)
    private String airportName;
    
    @Column(name = "Codigo", unique = true, nullable = false)
    private String code;
    
    @Column(name = "Ciudad", nullable = false)
    private String city;
    
    @Column(name = "Pais", nullable = false)
    private String country;
}
