package io.airboss.cms.security.auth;

import io.airboss.cms.users.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    
    private final UserRepository userRepository;
    
    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
              .map(user -> new SecurityUser(user))
              .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}