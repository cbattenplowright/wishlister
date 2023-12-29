package com.caldev.wishlister.services;

import java.util.Optional;

import com.caldev.wishlister.enums.RoleName;
import com.caldev.wishlister.models.Role;

import org.springframework.stereotype.Service;

import com.caldev.wishlister.repositories.RoleRepository;

@Service
public class RoleService {
    
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByRoleName(RoleName roleName) {
        Optional<Role> foundRole = roleRepository.findByRoleName(roleName);
        if (foundRole.isPresent()) {
            return foundRole.get();
        } else {
            throw new RuntimeException("Error: Role not found.");
        }
    }

}
