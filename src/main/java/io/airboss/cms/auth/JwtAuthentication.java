package io.airboss.cms.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class JwtAuthentication implements Authentication {
    
    private final String email;
    private final String role;
    private boolean authenticated = true;
    
    public JwtAuthentication(String email, String role) {
        this.email = email;
        this.role = role;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> role); // Devuelve el rol como autoridad
    }
    
    @Override
    public Object getCredentials() {
        return null; // No necesitamos credenciales aquí
    }
    
    @Override
    public Object getDetails() {
        return null; // No necesitamos detalles adicionales
    }
    
    @Override
    public Object getPrincipal() {
        return email; // El email del usuario autenticado
    }
    
    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }
    
    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }
    
    @Override
    public String getName() {
        return email;
    }
}
