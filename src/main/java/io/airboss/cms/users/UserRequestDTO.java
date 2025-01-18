package io.airboss.cms.users;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String username;
    private String password;
    private Long profileId; // Para identificar el perfil existente
    private String profileName;
    private String profileLastName;
    private String profileEmail;
    private Long profileMobile;
    private String profileImage;
    
    public UserRequestDTO() {
    }
    
    public UserRequestDTO(String username, String password, Long profileId, String profileName, String profileLastName, String profileEmail, Long profileMobile, String profileImage) {
        this.username = username;
        this.password = password;
        this.profileId = profileId;
        this.profileName = profileName;
        this.profileLastName = profileLastName;
        this.profileEmail = profileEmail;
        this.profileMobile = profileMobile;
        this.profileImage = profileImage;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Long getProfileId() {
        return profileId;
    }
    
    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }
    
    public String getProfileName() {
        return profileName;
    }
    
    public String getProfileLastName() {
        return profileLastName;
    }
    
    public String getProfileEmail() {
        return profileEmail;
    }
    
    public Long getProfileMobile() {
        return profileMobile;
    }
    
    public String getProfileImage() {
        return profileImage;
    }
}
