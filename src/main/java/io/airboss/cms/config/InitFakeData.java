//package io.airboss.cms.config;
//
//import com.github.javafaker.Faker;
//import io.airboss.cms.roles.Role;
//import io.airboss.cms.roles.RoleRepository;
//import io.airboss.cms.users.User;
//import io.airboss.cms.users.UserRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.Collections;
//
//@Component
//public class InitFakeData implements CommandLineRunner {
//
//    private final UserRepository userRepository;
//    private final RoleRepository roleRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public InitFakeData(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public void run(String... args) {
//        Faker faker = new Faker();
//
//        // Crear roles si no existen
//        Role userRole = roleRepository.findByName("ROLE_USER")
//              .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_USER")));
//
//        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
//              .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_ADMIN")));
//
//        // Crear usuario ADMIN con ROLE_ADMIN si no existe
//        if (userRepository.findByEmail("admin@airboss.com").isEmpty()) {
//            User admin = new User();
//            admin.setEmail("admin@airboss.com");
//            admin.setPassword(passwordEncoder.encode("admin123"));
//            admin.setRoles(Collections.singletonList(adminRole)); // Asignar rol de administrador
//            userRepository.save(admin);
//        }
//
//        // Crear 2 usuarios con ROLE_USER
//        for (int i = 0; i < 2; i++) {
//            String email = faker.internet().emailAddress();
//            if (userRepository.findByEmail(email).isEmpty()) {
//                User user = new User();
//                user.setEmail(email);
//                user.setPassword(passwordEncoder.encode("user123"));
//                user.setRoles(Collections.singletonList(userRole)); // Asignar rol de usuario
//                userRepository.save(user);
//            }
//        }
//
//        System.out.println("Datos iniciales cargados correctamente:");
//        System.out.println("- Usuario admin: admin@airboss.com / admin123");
//        System.out.println("- 2 usuarios con ROLE_USER creados.");
//    }
//}
