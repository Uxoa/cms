package io.airboss.cms.config;

import com.github.javafaker.Faker;
import io.airboss.cms.users.User;
import io.airboss.cms.users.UserRepository;
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
            User admin = new User();
            admin.setName("Admin");
            admin.setLastName("Admin");
            admin.setMobile(123456789L); // Número fijo para admin
            admin.setEmail("admin@airboss.com");
            admin.setPassword(passwordEncoder.encode("admin123")); // Contraseña codificada
            admin.setRole("ROLE_ADMIN"); // Asignar rol ADMIN
            admin.setProfileImage("https://via.placeholder.com/150"); // Imagen genérica
            admin.setRegistrationDate(LocalDateTime.now());
            admin.setLastLogin(LocalDateTime.now());
            userRepository.save(admin);
        }
        
        // Crear 2 usuarios con ROL USER
        for (int i = 0; i < 2; i++) {
            String email = faker.internet().emailAddress();
            if (userRepository.findByEmail(email).isEmpty()) {
                User user = new User();
                user.setName(faker.name().firstName());
                user.setLastName(faker.name().lastName());
                user.setMobile(Long.parseLong(faker.phoneNumber().subscriberNumber(9))); // Número móvil de 9 dígitos
                user.setEmail(email);
                user.setPassword(passwordEncoder.encode("user123")); // Contraseña codificada
                user.setRole("ROLE_USER"); // Asignar rol USER
                user.setProfileImage("https://via.placeholder.com/150"); // Imagen genérica
                user.setRegistrationDate(LocalDateTime.now());
                user.setLastLogin(null); // Último login será null al inicio
                userRepository.save(user);
            }
        }
        
        System.out.println("Datos iniciales cargados correctamente:");
        System.out.println("- Usuario admin: admin@airboss.com / admin123");
        System.out.println("- 2 usuarios con ROL_USER creados.");
    }
}
