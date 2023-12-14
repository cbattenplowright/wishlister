package com.caldev.wishlister.services;

import com.caldev.wishlister.models.User;
import com.caldev.wishlister.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUserById(UUID userId) {
        Optional<User> foundUser = userRepository.findByUserId(userId);
        return foundUser;
    }
}
