package io.airboss.cms.users;

import io.airboss.cms.profiles.ProfileRequestDTO;
import lombok.Data;

@Data
public class UserRequestDTO {
    private String username;
    private String password;
    private ProfileRequestDTO profile;
    
    public UserRequestDTO() {
    }
    
    public UserRequestDTO(String username, String password, ProfileRequestDTO profile) {
        this.username = username;
        this.password = password;
        this.profile = profile;
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
    
    public ProfileRequestDTO getProfile() {
        return profile;
    }
    
    public void setProfile(ProfileRequestDTO profile) {
        this.profile = profile;
    }
}
