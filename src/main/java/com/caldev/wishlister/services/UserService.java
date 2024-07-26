package com.caldev.wishlister.services;

import com.caldev.wishlister.dtos.NewUserDto;
import com.caldev.wishlister.entities.Authority;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.repositories.AuthorityRepository;
import com.caldev.wishlister.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private final AuthorityRepository authorityRepository;

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
    }

    public List<UserAccount> getAllUsers(){
        return userRepository.findAll();
    }

    public UserAccount getUserById(UUID requestedId){
        return userRepository.findById(requestedId).orElse(null);
    }

    public UserAccount createUser(NewUserDto newUserDto){
        Set<Authority> userAuthority = new HashSet<>(List.of(authorityRepository.findByRoleName("ROLE_USER")));

        UserAccount newUserAccount = new UserAccount(
                newUserDto.getUsername(),
                newUserDto.getPassword(),
                newUserDto.getName(),
                newUserDto.getEmail(),
                newUserDto.getDateOfBirth(),
                userAuthority
                );
        return userRepository.save(newUserAccount);
    }

    public void deleteUser(UUID requestedId){
        userRepository.deleteById(requestedId);
    }
}
