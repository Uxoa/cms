package io.airboss.cms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "Bienvenido al dashboard del administrador.";
    }
}
