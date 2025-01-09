package io.airboss.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThymeleafController {
    
    @GetMapping("/thymeleaf-demo")
    public String showDemoPage(Model model) {
        model.addAttribute("message", "¡Hola desde Thymeleaf!");
        return "demo"; // Apunta a templates/demo.html
    }
}
