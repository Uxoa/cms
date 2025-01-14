package io.airboss.cms.airports.airplanes;


import jakarta.persistence.*;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Entity
@Table(name = "airplanes")
public class Airplane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return id;
    }
}
