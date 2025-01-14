package io.airboss.cms.profiles;

import io.airboss.cms.profiles.Profile;
import io.airboss.cms.profiles.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    
    public Profile createProfile(Profile profile) {
        return profileRepository.save(profile);
    }
    
    public Profile getProfileById(Long id) {
        return profileRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("Perfil no encontrado: " + id));
    }
    
    public Profile updateProfile(Long id, Profile updatedProfile) {
        Profile profile = getProfileById(id);
        profile.setName(updatedProfile.getName());
        profile.setLastName(updatedProfile.getLastName());
        profile.setEmail(updatedProfile.getEmail());
        profile.setMobile(updatedProfile.getMobile());
        return profileRepository.save(profile);
    }
    
    public void deleteProfile(Long id) {
        Profile profile = getProfileById(id);
        profileRepository.delete(profile);
    }
}
