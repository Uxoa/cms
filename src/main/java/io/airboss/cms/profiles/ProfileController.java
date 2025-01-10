package io.airboss.cms.profiles;

import io.airboss.cms.users.User;
import io.airboss.cms.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
public class ProfileController {
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;
    
    @GetMapping("/profile")
    public String userProfile(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
              .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        model.addAttribute("user", user);
        return "profile"; // Nombre de la vista HTML (por ejemplo, profile.html en templates)
    }
    
    @PostMapping("/profile-image")
    public String uploadProfileImage(@RequestParam("image") MultipartFile file, Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
              .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Guardar la imagen en /static/img
        String fileName = saveImage(file);
        profileRepository.setProfileImage(fileName, user.getProfile().getProfileId());
 
        userRepository.save(user);
        
        model.addAttribute("user", user);
        model.addAttribute("message", "Imagen de perfil actualizada correctamente.");
        return "profile"; // Redirige a la vista del perfil
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
}
