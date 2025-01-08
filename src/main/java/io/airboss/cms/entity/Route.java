package io.airboss.cms.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "routes")
public class Route implements Serializable {
    
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "AeropuertoOrigenID", nullable = false)
    private Integer aeropuertoOrigenID;
    
    @Column(name = "AeropuertoDestinoID", nullable = false)
    private Integer aeropuertoDestinoID;
    
    @Column(name = "DuracionEstimada", nullable = false)
    private Integer duracionEstimada;
    
    @Column(name = "frecuencia")
    private String frecuencia;
    
    @Column(name = "Activo")
    private Integer activo;
    
    
    public Route(Integer id, Integer aeropuertoOrigenID, Integer aeropuertoDestinoID, Integer duracionEstimada, String frecuencia, Integer activo) {
        this.id = id;
        this.aeropuertoOrigenID = aeropuertoOrigenID;
        this.aeropuertoDestinoID = aeropuertoDestinoID;
        this.duracionEstimada = duracionEstimada;
        this.frecuencia = frecuencia;
        this.activo = activo;
    }
    
    public Route() {
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getAeropuertoOrigenID() {
        return aeropuertoOrigenID;
    }
    
    public void setAeropuertoOrigenID(Integer aeropuertoOrigenID) {
        this.aeropuertoOrigenID = aeropuertoOrigenID;
    }
    
    public Integer getAeropuertoDestinoID() {
        return aeropuertoDestinoID;
    }
    
    public void setAeropuertoDestinoID(Integer aeropuertoDestinoID) {
        this.aeropuertoDestinoID = aeropuertoDestinoID;
    }
    
    public Integer getDuracionEstimada() {
        return duracionEstimada;
    }
    
    public void setDuracionEstimada(Integer duracionEstimada) {
        this.duracionEstimada = duracionEstimada;
    }
    
    public String getFrecuencia() {
        return frecuencia;
    }
    
    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }
    
    public Integer getActivo() {
        return activo;
    }
    
    public void setActivo(Integer activo) {
        this.activo = activo;
    }
}
