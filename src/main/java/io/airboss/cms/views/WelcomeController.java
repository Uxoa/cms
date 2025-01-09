package io.airboss.cms.views;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    @GetMapping("/welcome")
    public String sayHii(Authentication authentication)
    {
        return "Welcome "+ authentication.getName() + " to Spring Security.";
    }
    
}