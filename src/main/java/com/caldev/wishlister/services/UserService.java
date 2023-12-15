package com.caldev.wishlister.services;

import com.caldev.wishlister.models.Role;
import com.caldev.wishlister.models.User;
import com.caldev.wishlister.repositories.RoleRepository;
import com.caldev.wishlister.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUserById(UUID userId) {
        Optional<User> foundUser = userRepository.findByUserId(userId);
        if (isAuthenticatedToViewUserDetails(foundUser.get(), userId)) {
            return foundUser;
        } else {
            return null;
        }

    }

    public boolean isAuthenticatedToViewUserDetails(User user, UUID requestedUserId) {
        // Check if User has User or Admin privileges
        // If user has Admin can view user details
        // if user is user and userId matches the userId specified view user
        // else FORBIDDEN

        Set<Role> roles = user.getRoles();
        Role adminRole = roleRepository.getReferenceById(2L);
        Role userRole = roleRepository.getReferenceById(1L);
        if (roles.contains(adminRole)) {
            return true;
        } else if (roles.contains(userRole) && (user.getUserId() == requestedUserId)) {
            return true;
        } else {
            return false;
        }
    }
}
