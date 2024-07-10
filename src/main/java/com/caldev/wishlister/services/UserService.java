package com.caldev.wishlister.services;

import com.caldev.wishlister.dtos.NewUserDto;
import com.caldev.wishlister.entities.Role;
import com.caldev.wishlister.entities.User;
import com.caldev.wishlister.repositories.RoleRepository;
import com.caldev.wishlister.repositories.UserManagementRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private final RoleRepository roleRepository;

    private final UserManagementRepository userManagementRepository;

    public UserService(UserManagementRepository userManagementRepository, RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.userManagementRepository = userManagementRepository;
    }

    public List<User> getAllUsers(){
        return userManagementRepository.findAll();
    }

    public User getUserById(UUID requestedId){
        return userManagementRepository.findById(requestedId).orElse(null);
    }

    public User createUser(NewUserDto newUserDto){
        Set<Role> userRole = new HashSet<>(List.of(roleRepository.findByRoleName("ROLE_USER")));

        User newUser = new User(
                newUserDto.getUsername(),
                newUserDto.getPassword(),
                newUserDto.getName(),
                newUserDto.getEmail(),
                newUserDto.getDateOfBirth(),
                userRole
                );
        return userManagementRepository.save(newUser);
    }

    public void deleteUser(UUID requestedId){
        userManagementRepository.deleteById(requestedId);
    }
}
