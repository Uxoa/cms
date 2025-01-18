package io.airboss.cms.users;

import io.airboss.cms.profiles.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private ProfileService profileService;
    
    @Autowired
    private ProfileMapper profileMapper;
    
    @Autowired
    private ProfileRepository profileRepository;
    
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        
        if (userRepository.findByUsername(userRequestDTO.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists: " + userRequestDTO.getUsername());
        }
        
        User user = userMapper.toEntity(userRequestDTO);
        User savedUser = userRepository.save(user); // Save user first
        
        ProfileResponseDTO profileResponseDTO = null;
        if (userRequestDTO.getProfileId() != null && userRequestDTO.getProfileId().longValue() > 0) {
            profileResponseDTO = profileService.getProfileById(userRequestDTO.getProfileId());
        }
        
        if (profileResponseDTO != null) {
            ProfileRequestDTO profileRequestDTO = new ProfileRequestDTO();
            profileRequestDTO.setId(profileResponseDTO.getId());
            profileRequestDTO.setName(profileResponseDTO.getName());
            profileRequestDTO.setLastName(profileResponseDTO.getLastName());
            profileRequestDTO.setEmail(profileResponseDTO.getEmail());
            profileRequestDTO.setMobile(profileResponseDTO.getMobile());
            profileRequestDTO.setProfileImage(profileResponseDTO.getProfileImage());
            profileRequestDTO.setRegistrationDate(profileResponseDTO.getRegistrationDate());
            profileRequestDTO.setLastLogin(profileResponseDTO.getLastLogin());
            profileRequestDTO.setUserId(savedUser.getId());
            
            Profile profile = profileMapper.toEntity(profileRequestDTO);
            profile.setUser(savedUser); // Set saved user
            savedUser.setProfile(profile);
            userRepository.save(savedUser); // Save user again with profile
        }
        
        return userMapper.toResponseDTO(savedUser);
    }
    
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        // Buscar el usuario existente
        User user = userRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Actualizar las propiedades básicas del usuario
        userMapper.updateEntity(user, userRequestDTO);
        
        if (userRequestDTO.getProfileId() != null) {
            // Recuperar el perfil existente
            ProfileResponseDTO profile = profileService.getProfileById(userRequestDTO.getProfileId());
            
            if (profile == null) {
                throw new RuntimeException("Profile not found for id: " + userRequestDTO.getProfileId());
            }
            
            // Actualizar los campos del perfil solo si están presentes
            if (userRequestDTO.getProfileName() != null) profile.setName(userRequestDTO.getProfileName());
            if (userRequestDTO.getProfileLastName() != null) profile.setLastName(userRequestDTO.getProfileLastName());
            if (userRequestDTO.getProfileEmail() != null) profile.setEmail(userRequestDTO.getProfileEmail());
            if (userRequestDTO.getProfileMobile() != null) profile.setMobile(userRequestDTO.getProfileMobile());
            if (userRequestDTO.getProfileImage() != null) profile.setProfileImage(userRequestDTO.getProfileImage());
            
            // Relacionar el perfil con el usuario
            user.setProfile(profileMapper.toEntity(profile));
            
            // Guardar el perfil actualizado
            profileRepository.save(profileMapper.toEntity(profile));
        }
        
        // Guardar el usuario actualizado
        User updatedUser = userRepository.save(user);
        return userMapper.toResponseDTO(updatedUser);
    }
    
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
    
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
              .map(userMapper::toResponseDTO)
              .collect(Collectors.toList());
    }
    
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toResponseDTO(user);
    }
}
