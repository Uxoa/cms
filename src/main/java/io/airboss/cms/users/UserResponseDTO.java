package io.airboss.cms.users;

import io.airboss.cms.profiles.ProfileResponseDTO;
import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String username;
    private ProfileResponseDTO profile;
    
    public ProfileResponseDTO getProfile() {
        return profile;
    }
    
    public void setProfile(ProfileResponseDTO profile) {
        this.profile = profile;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
}
