package com.caldev.wishlister.services;

import com.caldev.wishlister.dtos.NewUserDto;
import com.caldev.wishlister.entities.Role;
import com.caldev.wishlister.entities.User;
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

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(UUID requestedId){
        return userRepository.findById(requestedId).orElse(null);
    }

    public User createUser(NewUserDto newUserDto){
        Set<Role> userRole = new HashSet<Role>(List.of(roleRepository.findByRoleName("ROLE_USER")));

        User newUser = new User(
                newUserDto.getUsername(),
                newUserDto.getPassword(),
                newUserDto.getName(),
                newUserDto.getEmail(),
                newUserDto.getDateOfBirth(),
                userRole
                );
        return userRepository.save(newUser);
    }

    public void deleteUser(UUID requestedId){
        userRepository.deleteById(requestedId);
    }
}
