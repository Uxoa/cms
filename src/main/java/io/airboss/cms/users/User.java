package io.airboss.cms.users;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.airboss.cms.profiles.Profile;
import io.airboss.cms.roles.Role;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", referencedColumnName = "profileId")
    @JsonManagedReference // Controla la serialización en el lado propietario
    private Profile profile;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
          name = "user_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;
    
    // Constructores
    public User() {}
    
    public User(String username, String email, String password, Profile profile, List<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.profile = profile;
        this.roles = roles;
    }
    
    // Getters y setters
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Profile getProfile() {
        return profile;
    }
    
    public void setProfile(Profile profile) {
        this.profile = profile;
        if (profile != null) {
            profile.setUser(this); // Establecer bidireccionalidad
        }
    }
    
    public List<Role> getRoles() {
        return roles;
    }
    
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
