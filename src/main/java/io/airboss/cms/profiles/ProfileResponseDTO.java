package io.airboss.cms.profiles;

import java.time.LocalDateTime;

public class ProfileResponseDTO {
    
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private Long mobile;
    private String profileImage;
    private LocalDateTime registrationDate;
    private LocalDateTime lastLogin;
    private Long userId;
    
    // Constructor vacío
    public ProfileResponseDTO() {
    }
    
    // Constructor con todos los campos
    public ProfileResponseDTO(Long id, String name, String lastName, String email, Long mobile, String profileImage,
                              LocalDateTime registrationDate, LocalDateTime lastLogin) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.profileImage = profileImage;
        this.registrationDate = registrationDate;
        this.lastLogin = lastLogin;
        this.userId = userId;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    
    
    @Override
    public String toString() {
        return "ProfileResponseDTO{" +
              "id=" + id +
              ", name='" + name + '\'' +
              ", lastName='" + lastName + '\'' +
              ", email='" + email + '\'' +
              ", mobile=" + mobile +
              ", profileImage='" + profileImage + '\'' +
              ", registrationDate=" + registrationDate +
              ", lastLogin=" + lastLogin +
              ", userId=" + userId +
              '}';
    }
    
    
  
}
