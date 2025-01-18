package io.airboss.cms.profiles;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
public class ProfileService {
    
    @Autowired
    private ProfileRepository profileRepository;
    
    @Autowired
    private ProfileMapper profileMapper;
    
    // Método para crear un perfil a partir de un DTO
    public ProfileResponseDTO createProfile(ProfileRequestDTO profileRequestDTO) {
        Profile profile = profileMapper.toEntity(profileRequestDTO);
        Profile savedProfile = profileRepository.save(profile);
        return profileMapper.toResponseDTO(savedProfile);
    }
    
    // Método para crear un perfil predeterminado
    public ProfileResponseDTO createDefaultProfile() {
        Profile profile = new Profile();
        profile.setName("Default");
        profile.setLastName("User");
        profile.setEmail("default@example.com");
        profile.setMobile(123456789L);
        profile.setRegistrationDate(LocalDateTime.now());
        profile.setLastLogin(null);
        
        Profile savedProfile = profileRepository.save(profile);
        return profileMapper.toResponseDTO(savedProfile);
    }
    
    // Obtener un perfil por su ID
    public ProfileResponseDTO getProfileById(Long id) {
        Profile profile = profileRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("Profile not found with id: " + id));
        return profileMapper.toResponseDTO(profile);
    }
    
    // Actualizar un perfil existente
    public ProfileResponseDTO updateProfile(Long id, ProfileRequestDTO updatedProfileDTO) {
        Profile existingProfile = profileRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("Profile not found with id: " + id));
        
        // Mapear las propiedades actualizadas al perfil existente
        existingProfile.setName(updatedProfileDTO.getName());
        existingProfile.setLastName(updatedProfileDTO.getLastName());
        existingProfile.setEmail(updatedProfileDTO.getEmail());
        existingProfile.setMobile(updatedProfileDTO.getMobile());
        existingProfile.setProfileImage(updatedProfileDTO.getProfileImage());
        existingProfile.setLastLogin(updatedProfileDTO.getLastLogin());
        
        Profile updatedProfile = profileRepository.save(existingProfile);
        return profileMapper.toResponseDTO(updatedProfile);
    }
    
    // Eliminar un perfil por su ID
    public void deleteProfile(Long id) {
        if (!profileRepository.existsById(id)) {
            throw new RuntimeException("Profile not found with id: " + id);
        }
        profileRepository.deleteById(id);
    }
}
