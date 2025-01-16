package io.airboss.cms.users;

import io.airboss.cms.profiles.Profile;
import io.airboss.cms.profiles.ProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    
    @Autowired
    private ProfileMapper profileMapper;
    @Autowired
    private UserRepository userRepository;
    
    public User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        
        if (dto.getProfile() != null) {
            Profile profile = profileMapper.toEntity(dto.getProfile());
            user.setProfile(profile);
        }
        
        return user;
    }
    
    public UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        
        if (user.getProfile() != null) {
            dto.setProfile(profileMapper.toResponse(user.getProfile()));
        }
        
        return dto;
    }
    
    public void updateEntity(User user, UserRequestDTO dto) {
        if (dto.getUsername() != null) {
            user.setUsername(dto.getUsername());
        }
        if (dto.getPassword() != null) {
            user.setPassword(dto.getPassword());
        }
        if (dto.getProfile() != null) {
            user.setProfile(profileMapper.toEntity(dto.getProfile()));
        }
    }
    
    public UserResponseDTO toResponse(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        
        if (user.getProfile() != null) {
            dto.setProfile(profileMapper.toResponse(user.getProfile()));
        }
        
        return dto;
    }
}