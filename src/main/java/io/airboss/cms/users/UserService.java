package io.airboss.cms.users;

import io.airboss.cms.roles.Role;
import io.airboss.cms.roles.RoleRepository;
import io.airboss.cms.profiles.Profile;
import io.airboss.cms.profiles.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private ProfileRepository profileRepository;
    
    @Transactional
    public User createUser(User user, Profile profile, List<String> roles) {
        // Asignar roles
        Set<Role> roleEntities = roles.stream()
              .map(roleName -> roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + roleName)))
              .collect(Collectors.toSet());
        user.setRoles(roleEntities);
        
        // Asociar perfil al usuario
        profile.setUser(user);
        profileRepository.save(profile);
        
        // Guardar usuario
        return userRepository.save(user);
    }
    
    public User getUserById(Long id) {
        return userRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public User updateUser(Long id, User updatedUser) {
        User existingUser = getUserById(id);
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setPassword(updatedUser.getPassword());
        return userRepository.save(existingUser);
    }
    
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
}
