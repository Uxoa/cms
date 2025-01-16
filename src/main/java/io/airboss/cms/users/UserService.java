package io.airboss.cms.users;

import io.airboss.cms.profiles.ProfileService;
import io.airboss.cms.profiles.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private ProfileService profileService;
    
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        Profile profile = null;
        if (userRequestDTO.getProfile() != null && userRequestDTO.getProfile().getId() != null) {
            profile = profileService.getProfileById(userRequestDTO.getProfile().getId());
        }
        
        User user = userMapper.toEntity(userRequestDTO);
        user.setProfile(profile);
        
        User savedUser = userRepository.save(user);
        return userMapper.toResponseDTO(savedUser);
    }
    
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("User not found"));
        
        userMapper.updateEntity(user, userRequestDTO);
        
        if (userRequestDTO.getProfile() != null && userRequestDTO.getProfile().getId() != null) {
            Profile profile = profileService.getProfileById(userRequestDTO.getProfile().getId());
            user.setProfile(profile);
        }
        
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