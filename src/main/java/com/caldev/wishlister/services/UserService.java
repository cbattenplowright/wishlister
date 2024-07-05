package com.caldev.wishlister.services;

import com.caldev.wishlister.entities.User;
import com.caldev.wishlister.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(UUID requestedId){
        return userRepository.findById(requestedId).orElse(null);
    }
}
