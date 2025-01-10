package io.airboss.cms.security.auth;

import io.airboss.cms.users.User;
import io.airboss.cms.users.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    
    private final UserRepository userRepository;
    
    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
              .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return new SecurityUser(user);
    }
}
