package io.airboss.cms.profiles;

import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {
    
    public Profile toEntity(ProfileRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Profile profile = new Profile();
        profile.setName(dto.getName());
        profile.setLastName(dto.getLastName());
        profile.setEmail(dto.getEmail());
        profile.setMobile(Long.valueOf(dto.getMobile()));
        return profile;
    }
    
    public ProfileResponseDTO toResponse(Profile profile) {
        if (profile == null) {
            return null;
        }
        ProfileResponseDTO response = new ProfileResponseDTO();
        response.setName(profile.getName());
        response.setLastName(profile.getLastName());
        response.setEmail(profile.getEmail());
        response.setMobile(profile.getMobile());
        return response;
    }
}
