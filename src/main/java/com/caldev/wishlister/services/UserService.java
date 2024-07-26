package com.caldev.wishlister.services;

import com.caldev.wishlister.dtos.NewUserDto;
import com.caldev.wishlister.entities.Role;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.repositories.RoleRepository;
import com.caldev.wishlister.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public List<UserAccount> getAllUsers(){
        return userRepository.findAll();
    }

    public UserAccount getUserById(UUID requestedId){
        return userRepository.findById(requestedId).orElse(null);
    }

    public UserAccount createUser(NewUserDto newUserDto){
        Set<Role> userRole = new HashSet<>(List.of(roleRepository.findByRoleName("ROLE_USER")));

        UserAccount newUserAccount = new UserAccount(
                newUserDto.getUsername(),
                newUserDto.getPassword(),
                newUserDto.getName(),
                newUserDto.getEmail(),
                newUserDto.getDateOfBirth(),
                userRole
                );
        return userRepository.save(newUserAccount);
    }

    public void deleteUser(UUID requestedId){
        userRepository.deleteById(requestedId);
    }
}
