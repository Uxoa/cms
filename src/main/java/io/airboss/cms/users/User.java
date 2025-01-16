package io.airboss.cms.users;

import io.airboss.cms.profiles.Profile;
import io.airboss.cms.roles.Role;
import io.airboss.cms.bookings.Booking;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Profile profile;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
          name = "user_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Booking> bookings;
    
    
    
    public User(String username,  String password) {
        this.username = username;
        this.password = password;
    }
    
    public User() {
    
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    
    public String getPassword() {
        return password;
    }
    
    public Profile getProfile() {
        return profile;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    
    
    public Set<Role> getRoles() {
        return roles;
    }
    
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
    public Set<Booking> getBookings() {
        return bookings;
    }
    
    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }
    
    public void addBooking(Booking booking) {
        bookings.add(booking);
        booking.setUser(this);
    }
    
    public void removeBooking(Booking booking) {
        bookings.remove(booking);
        booking.setUser(null);
    }
    
  
}
