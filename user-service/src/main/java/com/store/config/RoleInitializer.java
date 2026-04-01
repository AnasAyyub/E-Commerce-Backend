package com.store.config;


import com.store.models.Role;
import com.store.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer {

    private RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
    }

    @PostConstruct
    public void initRoles() {
        if (!roleRepository.existsByName("ROLE_ADMIN")){
            Role role = new Role();
            role.setValue("ROLE_ADMIN");
            roleRepository.save(role);
        }

        if (!roleRepository.existsByName("ROLE_USER")){
            Role role = new Role();
            role.setValue("ROLE_USER");
            roleRepository.save(role);
        }
    }
}
