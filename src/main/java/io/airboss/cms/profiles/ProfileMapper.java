package io.airboss.cms.profiles;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    
    Profile toEntity(ProfileRequestDTO profileRequestDTO);
    
    Profile toEntity(ProfileResponseDTO profileResponseDTO);
    
    ProfileResponseDTO toResponseDTO(Profile profile);
    
}
