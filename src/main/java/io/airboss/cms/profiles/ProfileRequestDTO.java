package io.airboss.cms.profiles;

public class ProfileRequestDTO {
    
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private Long mobile;
    
    
    // Getters y setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Long getMobile() {
        return mobile;
    }
    
    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }
    
    @Override
    public String toString() {
        return "ProfileRequestDTO{" +
              "name='" + name + '\'' +
              ", lastName='" + lastName + '\'' +
              ", email='" + email + '\'' +
              ", mobile=" + mobile +
              '}';
    }
}
