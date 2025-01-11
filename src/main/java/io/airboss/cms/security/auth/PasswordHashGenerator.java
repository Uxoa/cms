package io.airboss.cms.security.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "password"; // Cambia por la contraseña que deseas codificar
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println(encodedPassword); // Copia este hash y úsalo en tu base de datos
    }
}
