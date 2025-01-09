package io.airboss.cms.controller;

import io.airboss.cms.entity.User;
import io.airboss.cms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/profile")
    public String userProfile(Authentication authentication) {
        return "Bienvenido, " + authentication.getName() + " !  Este es tu perfil.";
    }
    
    @PostMapping("/profile-image")
    public String uploadProfileImage(@RequestParam("image") MultipartFile file, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
              .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Guardar la imagen en /static/img
        String fileName = saveImage(file);
        user.setProfileImage("/img/" + fileName); // Ruta relativa a /static
        userRepository.save(user);
        
        return "Imagen de perfil actualizada correctamente: http://localhost:8080/img/" + fileName;
    }
    
    
    private String saveImage(MultipartFile file) {
        try {
            // Directorio donde se guardarán las imágenes
            String uploadDir = "src/main/resources/static/img/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            // Generar un nombre único para la imagen
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);
            
            // Guardar la imagen en el disco
            Files.write(filePath, file.getBytes());
            
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }
    }
    
    
    
    //vistas
    @GetMapping("/user/profile")
    public String userProfile(Authentication authentication, Model model) {
        model.addAttribute("userName", authentication.getName());
        return "user-profile"; // Renderizará templates/user-profile.html
    }
    
    
}
