package ua.dragunovskiy.web_security_2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/unsecured")
    public String unsecuredData() {
        return "unsecured data";
    }

    @GetMapping("/secured")
    public String securedData() {
        return "secured data";
    }

    @GetMapping("/admin")
    public String adminData() {
        return "admin data";
    }

    @GetMapping("/info")
    public String userData(Principal principal) {
        return principal.getName();
    }
}
