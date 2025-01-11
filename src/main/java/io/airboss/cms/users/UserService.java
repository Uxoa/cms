package io.airboss.cms.users;

import io.airboss.cms.security.jwt.LoginRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private final UserRepository userRepository;
    
    @Autowired
    private final PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Transactional
    public void login(LoginRequest request) {
        String username = request.getUsername();
        Optional<User> existingUser = userRepository.findByUsername(username);
        
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            // Verificar la contraseña con el método matches
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                System.out.println("Usuario autenticado: " + username);
            } else {
                System.out.println("Contraseña incorrecta");
            }
        } else {
            System.out.println("Usuario no encontrado");
        }
    }
}
