package io.airboss.cms.profiles;

import jakarta.transaction.Transactional;

public class ProfileService {
    private final ProfileRepository profileRepository;
    
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }
    
    @Transactional
    public void updateProfileImage(Long id, String profileImage) {
        profileRepository.setProfileImage(profileImage, id);
    }
}
