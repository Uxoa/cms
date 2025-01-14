package io.airboss.cms.users;

import io.airboss.cms.profiles.Profile;
import io.airboss.cms.roles.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping
    public ResponseEntity<User> createUser(
          @RequestBody User user,
          @RequestBody Profile profile,
          @RequestBody List<String> roles) {
        
        User createdUser = userService.createUser(user, profile, roles);
        return ResponseEntity.ok(createdUser);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
