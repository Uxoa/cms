package io.airboss.cms.filter;

import io.airboss.cms.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
          throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            if (jwtUtil.validateToken(token)) {
                String email = jwtUtil.extractEmail(token);
                String role = jwtUtil.extractRole(token);
                
                User user = new User(email, "", List.of(() -> role));
                SecurityContextHolder.getContext().setAuthentication(
                      new org.springframework.security.core.Authentication() {
                          @Override
                          public Collection<? extends GrantedAuthority> getAuthorities() {
                              return List.of(() -> role);
                          }
                          
                          @Override
                          public Object getCredentials() {
                              return null;
                          }
                          
                          @Override
                          public Object getDetails() {
                              return null;
                          }
                          
                          @Override
                          public Object getPrincipal() {
                              return user;
                          }
                          
                          @Override
                          public boolean isAuthenticated() {
                              return true;
                          }
                          
                          @Override
                          public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
                          }
                          
                          @Override
                          public String getName() {
                              return email;
                          }
                      });
            }
        }
        
        chain.doFilter(request, response);
    }
}
