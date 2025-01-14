package io.airboss.cms.profiles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.airboss.cms.users.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private Long mobile;
    
    @Column
    private String profileImage;
    
    @Column(nullable = false)
    private LocalDateTime registrationDate;
    
    @Column
    private LocalDateTime lastLogin;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore // Evita bucles de serialización
    private User user;
    
    // Constructores
    public Profile() {}
    
    public Profile(String name, String lastName,String email,  Long mobile, String profileImage,
                   LocalDateTime registrationDate, LocalDateTime lastLogin) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.profileImage = profileImage;
        this.registrationDate = registrationDate;
        this.lastLogin = lastLogin;
    }
    
    // Getters y setters
    public Long getProfileId() {
        return profileId;
    }
    
    public void setProfileId(Long profileId) {
        this.profileId = profileId;
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
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Long getMobile() {
        return mobile;
    }
    
    public void setMobile(Long mobile) {
        this.mobile = mobile;
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
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Un perfil debe estar asociado a un usuario.");
        }
        this.user = user;
    }
    
}
