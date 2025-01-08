package io.airboss.cms.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @GetMapping("/profile")
    public String userProfile(Authentication authentication) {
        return "Bienvenido, " + authentication.getName() + "! Este es tu perfil.";
    }
}

