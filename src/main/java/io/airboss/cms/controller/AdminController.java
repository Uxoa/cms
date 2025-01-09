package io.airboss.cms.controller;

import io.airboss.cms.entity.User;
import io.airboss.cms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "Bienvenido al dashboard del administrador.";
    }
    
    // Obtener lista de todos los usuarios
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    // Crear un nuevo usuario
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        user.setPassword("{encoded_password}"); // Reemplazar con contraseña encriptada
        return userRepository.save(user);
    }
    
    // Actualizar un usuario existente
    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        User user = userOptional.get();
        user.setName(updatedUser.getName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        user.setRole(updatedUser.getRole());
        return userRepository.save(user);
    }
    
    // Eliminar un usuario
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
    
    //vistas
    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("welcomeMessage", "Bienvenido al Dashboard de Administrador");
        return "admin-dashboard"; // Renderizará templates/admin-dashboard.html
    }
    
}
