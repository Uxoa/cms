package io.airboss.cms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "routes")
public class Route implements Serializable {
    
    @Id
    @Column(name = "route_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer routeId;
    
    @Column(name = "aeropuerto_origen_id", nullable = false)
    private Integer aeropuertoOrigenID;
    
    @Column(name = "aeropuerto_destino_id", nullable = false)
    private Integer aeropuertoDestinoID;
    
    @Column(name = "duracion_estimada", nullable = false)
    private Integer duracionEstimada;
    
    @Column(name = "frecuencia")
    private String frecuencia;
    
    @Column(name = "Activo")
    private Integer activo;
    
}
