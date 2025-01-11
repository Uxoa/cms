package io.airboss.cms.users;

import io.airboss.cms.roles.Role;
import io.airboss.cms.roles.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword())); // Encriptar contraseña
        
        // Asignar roles predeterminados
        Role defaultRole = roleRepository.findByName("ROLE_USER")
              .orElseThrow(() -> new RuntimeException("Default role not found"));
        user.setRoles(List.of(defaultRole));
        
        userRepository.save(user);
        return ResponseEntity.ok("Usuario agregado correctamente con rol predeterminado");
    }
}
