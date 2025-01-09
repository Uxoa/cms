package io.airboss.cms.users;

import jakarta.persistence.*;
import java.time.LocalDateTime;

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
    
    @Column(nullable = false, unique = true)
    private String username;
    
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
    
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public long getMobile() {
        return mobile;
    }
    
    public void setMobile(long mobile) {
        this.mobile = mobile;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getProfileImage() {
        return profileImage;
    }
    
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
    
    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }
    
    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    public LocalDateTime getLastLogin() {
        return lastLogin;
    }
    
    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
    
    public User(Long userId, String name, String lastName, long mobile, String email, String password, String role, String profileImage, LocalDateTime registrationDate, LocalDateTime lastLogin) {
        this.userId = userId;
        this.name = name;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.role = role;
        this.profileImage = profileImage;
        this.registrationDate = registrationDate;
        this.lastLogin = lastLogin;
    }
    
    
    public User() {
    }
    
    
}
