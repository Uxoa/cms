package io.airboss.cms.users;

import io.airboss.cms.profiles.ProfileMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class})
public interface UserMapper {
    
    User toEntity(UserRequestDTO userRequestDTO);
    
    UserResponseDTO toResponseDTO(User user);
    
    void updateEntity(@MappingTarget User user, UserRequestDTO userRequestDTO);
}
