package io.airboss.cms.profiles;

import io.airboss.cms.users.User;
import io.airboss.cms.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProfileRepository profileRepository;
    
    // Obtener el perfil del usuario autenticado
    @GetMapping
    public ResponseEntity<Profile> getUserProfile(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByUsername(email)
              .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        Profile profile = user.getProfile();
        return ResponseEntity.ok(profile);
    }
    
    // Crear un nuevo perfil
    @PostMapping
    public ResponseEntity<String> createProfile(@RequestBody Profile profile) {
        profile.setRegistrationDate(LocalDateTime.now());
        profileRepository.save(profile);
        return ResponseEntity.ok("Perfil creado exitosamente.");
    }
    
    // Actualizar un perfil existente
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProfile(@PathVariable Long id, @RequestBody Profile updatedProfile) {
        Profile profile = profileRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
        
        profile.setName(updatedProfile.getName());
        profile.setLastName(updatedProfile.getLastName());
        profile.setMobile(updatedProfile.getMobile());
        profileRepository.save(profile);
        
        return ResponseEntity.ok("Perfil actualizado exitosamente.");
    }
    
    // Eliminar un perfil
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProfile(@PathVariable Long id) {
        Profile profile = profileRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
        
        profileRepository.delete(profile);
        return ResponseEntity.ok("Perfil eliminado exitosamente.");
    }
    
    // Subir una imagen de perfil
    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadProfileImage(@RequestParam("image") MultipartFile file, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByUsername(email)
              .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Guardar la imagen
        String fileName = saveImage(file);
        user.getProfile().setProfileImage(fileName);
        profileRepository.save(user.getProfile());
        
        return ResponseEntity.ok("Imagen de perfil actualizada correctamente.");
    }
    
    // Método para guardar la imagen en el sistema de archivos
    private String saveImage(MultipartFile file) {
        try {
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
