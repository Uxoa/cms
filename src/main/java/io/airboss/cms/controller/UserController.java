package io.airboss.cms.controller;

import io.airboss.cms.entity.User;
import io.airboss.cms.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {
    
    private UserRepository userRepository;
    
    @GetMapping("/profile")
    public String userProfile(Authentication authentication) {
        return "Bienvenido, " + authentication.getName() + "! Este es tu perfil.";
    }
    
    @PostMapping("/profile-image")
    public String uploadProfileImage(@RequestParam("image") MultipartFile file, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
              .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Lógica para guardar la imagen (local o nube)
        String imageUrl = saveImage(file);
        
        user.setProfileImage(imageUrl);
        userRepository.save(user);
        
        return "Imagen de perfil actualizada correctamente";
    }
    
    private String saveImage(MultipartFile file) {
        return "https://example.com/image.jpg";
    }
    
}
