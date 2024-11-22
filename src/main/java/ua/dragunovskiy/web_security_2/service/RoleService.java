package ua.dragunovskiy.web_security_2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dragunovskiy.web_security_2.entity.Role;
import ua.dragunovskiy.web_security_2.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
}
