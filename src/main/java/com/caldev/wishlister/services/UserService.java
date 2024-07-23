package com.caldev.wishlister.services;

import com.caldev.wishlister.dtos.NewUserDto;
//import com.caldev.wishlister.entities.Role;
import com.caldev.wishlister.entities.UserAccount;
//import com.caldev.wishlister.repositories.RoleRepository;
import com.caldev.wishlister.repositories.UserManagementRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

//    private final RoleRepository roleRepository;

    private final UserManagementRepository userManagementRepository;

    public UserService(UserManagementRepository userManagementRepository
//                       RoleRepository roleRepository
                       ) {
//        this.roleRepository = roleRepository;
        this.userManagementRepository = userManagementRepository;
    }

    public List<UserAccount> getAllUsers(){
        return userManagementRepository.findAll();
    }

    public UserAccount getUserById(UUID requestedId){
        return userManagementRepository.findById(requestedId).orElse(null);
    }

    public UserAccount getUserByUsername(String username){
        return userManagementRepository.findByUsername(username);
    }

    public UserAccount createUser(NewUserDto newUserDto){
        ArrayList<GrantedAuthority> userRoles = new ArrayList<>(List.of(new SimpleGrantedAuthority("ROLE_USER")));

        UserAccount newUserAccount = new UserAccount(
                newUserDto.getUsername(),
                newUserDto.getPassword(),
                newUserDto.getName(),
                newUserDto.getEmail(),
                newUserDto.getDateOfBirth(),
                userRoles
                );
        return userManagementRepository.save(newUserAccount);
    }

    public void deleteUser(UUID requestedId){
        userManagementRepository.deleteById(requestedId);
    }
}
