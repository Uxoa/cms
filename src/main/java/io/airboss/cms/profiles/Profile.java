package io.airboss.cms.profiles;

import io.airboss.cms.users.Role;
import io.airboss.cms.users.User;
import jakarta.persistence.*;
import org.hibernate.generator.values.GeneratedValues;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;
    
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(nullable = false)
    private Long mobile;
    
    @Column
    private String profileImage;
    
    @Column(nullable = false)
    private LocalDateTime registrationDate;
    
    @Column
    private LocalDateTime lastLogin;
    
    
    public Profile(Long profileId, User user, String name, String lastName, Long mobile, String profileImage, LocalDateTime registrationDate, LocalDateTime lastLogin) {
        this.profileId = profileId;
        this.user = user;
        this.name = name;
        this.lastName = lastName;
        this.mobile = mobile;
        this.profileImage = profileImage;
        this.registrationDate = registrationDate;
        this.lastLogin = lastLogin;
    }
    
    public Profile() {}
    
    public Long getProfileId() {
        return profileId;
    }
    
    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
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
}
