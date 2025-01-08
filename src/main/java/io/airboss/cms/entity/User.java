package io.airboss.cms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "nombre", nullable = false)
    private String name;
   
    @Column(name =  "apellido", nullable = false)
    private String lastName;
    
    @Column(name = "mobile")
    private long mobile;
    
    @Column(name = "email", unique = true,nullable = false)
    private String email;
    
    @Column(name = "contraseña", nullable = false)
    private String password;
    
    @Column(name = "rol", nullable = false)
    private String role;
    
    @Column(name = "imagen_perfil")
    private String profileImage;
    
    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime registrationDate;
    
    @Column(name = "ultimo_login")
    private LocalDateTime lastLogin;
    
}
