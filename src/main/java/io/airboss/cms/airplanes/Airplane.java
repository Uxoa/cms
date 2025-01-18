package io.airboss.cms.airplanes;


import jakarta.persistence.*;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Entity
@Table(name = "airplanes")
public class Airplane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airplanes_id")
    private Long airplanesId;
    
    public void setId(Long id) {
        this.airplanesId = id;
    }
    
    public Long getId() {
        return airplanesId;
    }
}
