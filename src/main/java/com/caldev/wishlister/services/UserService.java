package com.caldev.wishlister.services;

import com.caldev.wishlister.dtos.NewUserDto;
import com.caldev.wishlister.entities.Authority;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.exceptions.UserNotFoundException;
import com.caldev.wishlister.repositories.AuthorityRepository;
import com.caldev.wishlister.repositories.UserManagementRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final AuthorityRepository authorityRepository;

    private final UserManagementRepository userManagementRepository;

    public UserService(UserManagementRepository userManagementRepository, AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
        this.userManagementRepository = userManagementRepository;
    }

    public List<UserAccount> getAllUsers(){
        return userManagementRepository.findAll();
    }

    public UserAccount getUserById(UUID requestedId){
        return userManagementRepository.findById(requestedId).orElse(null);
    }

    public UserAccount createUser(NewUserDto newUserDto){
        Set<Authority> userAuthority = new HashSet<>(List.of(authorityRepository.findByAuthority("ROLE_USER")));

        UserAccount newUserAccount = new UserAccount(
                newUserDto.getEmail(),
                newUserDto.getName(),
                newUserDto.getPassword(),
                newUserDto.getDateOfBirth(),
                userAuthority
                );
        return userManagementRepository.save(newUserAccount);
    }

    public void deleteUser(UUID requestedId){
        if (userManagementRepository.findById(requestedId).isEmpty()){
            throw new UserNotFoundException("User not found with id: " + requestedId);
        }
        userManagementRepository.deleteById(requestedId);
    }
}
