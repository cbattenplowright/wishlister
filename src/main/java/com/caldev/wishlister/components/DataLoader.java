package com.caldev.wishlister.components;

import com.caldev.wishlister.entities.Role;
import com.caldev.wishlister.repositories.RoleRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader {

    private final RoleRepository roleRepository;

    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(roleRepository.count() == 0){
            List<Role> roles = Arrays.asList(new Role("ROLE_ADMIN"), new Role("ROLE_USER"));

            roleRepository.saveAll(roles);
        }
    }
}
