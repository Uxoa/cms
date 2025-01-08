package io.airboss.cms.config;

import com.github.javafaker.Faker;
import io.airboss.cms.entity.User;
import io.airboss.cms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InitFakeData implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        
        // Crear usuario ADMIN con ROL ADMIN
        if (userRepository.findByEmail("admin@airboss.com").isEmpty()) {
            User admin = User.builder()
                  .name("Admin")
                  .lastName("Admin")
                  .mobile(Long.parseLong(faker.phoneNumber().subscriberNumber(9))) // Número
                  // móvil de 10 dígitos
                  .email("admin@airboss.com")
                  .password(passwordEncoder.encode("admin123")) // Contraseña codificada
                  .role("ROLE_ADMIN") // Asegurar prefijo ROLE_
                  .profileImage("https://via.placeholder.com/150") // Imagen genérica
                  .registrationDate(LocalDateTime.now())
                  .lastLogin(LocalDateTime.now())
                  .build();
            userRepository.save(admin);
        }
        
        // Crear usuarios con ROL USER
        for (int i = 0; i < 2; i++) {
            String email = faker.internet().emailAddress();
            if (userRepository.findByEmail(email).isEmpty()) {
                User user = User.builder()
                      .name(faker.name().firstName())
                      .lastName(faker.name().lastName())
                      .mobile(Long.parseLong(faker.phoneNumber().subscriberNumber(9))) // Número
                      // móvil de 10 dígitos
                      .email(email)
                      .password(passwordEncoder.encode("user123")) // Contraseña codificada
                      .role("ROLE_USER") // Asegurar prefijo ROLE_
                      .profileImage("https://via.placeholder.com/150") // Imagen genérica
                      .registrationDate(LocalDateTime.now())
                      .lastLogin(null) // Último login será null al inicio
                      .build();
                userRepository.save(user);
            }
        }
    }
}
