package io.airboss.cms.users;

import io.airboss.cms.profiles.Profile;
import io.airboss.cms.profiles.ProfileRepository;
import io.airboss.cms.roles.Role;
import io.airboss.cms.roles.RoleRepository;
import io.airboss.cms.users.User;
import io.airboss.cms.users.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        // Validar si el usuario ya existe por email
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        
        // Crear perfil
        profile.setUser(user);
        user.setProfile(profile);
        
        // Asignar roles
        List<Role> roleEntities = roles.stream()
              .map(roleName -> roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + roleName)))
              .collect(Collectors.toList());
        user.setRoles(roleEntities);
        
        // Guardar usuario (cascada guarda el perfil)
        return userRepository.save(user);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public User getUserById(Long id) {
        return userRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));
    }
    
    public User updateUser(Long id, User updatedUser) {
        User user = getUserById(id);
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        return userRepository.save(user);
    }
    
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
