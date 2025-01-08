package io.airboss.cms.service;

import io.airboss.cms.repository.UserRepository;
import io.airboss.cms.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
              .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
        
        return org.springframework.security.core.userdetails.User
              .withUsername(user.getEmail()) // Usa email como username
              .password(user.getPassword()) // Contraseña encriptada
              .roles(user.getRole().replace("ROLE_", "")) // Remueve "ROLE_" para que coincida con Spring Security
              .build();
    }
}

