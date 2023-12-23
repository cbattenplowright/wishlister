package com.caldev.wishlister.services;

import com.caldev.wishlister.models.Role;
import com.caldev.wishlister.models.SecurityUserDetails;
import com.caldev.wishlister.models.User;
import com.caldev.wishlister.repositories.RoleRepository;
import com.caldev.wishlister.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
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

    // Work out how you will send back the User or ResponseEntity codes!!

    public User findUserById(UUID userId) {

        Optional<User> foundUser = userRepository.findByUserId(userId);
        return foundUser.orElse(null);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public boolean isAuthorizedToViewUserDetails(UUID requestedUserId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            SecurityUserDetails authenticatedUser = (SecurityUserDetails) authentication.getPrincipal();
            User user = authenticatedUser.getUser();

            Set<Role> roles = user.getRoles();
            long adminId = 2;
            long userId = 1;

            if (roles.stream().anyMatch(role -> role.getRoleId() == adminId)) {
                return true; // Admin is authorized to view any user details
            } else if (roles.stream().anyMatch(role -> role.getRoleId() == userId) && (user.getUserId().equals(requestedUserId))) {
                return true; // User is authorized to view their own user details
            }
        }
        return false; // User is not authorized to view user details
    }
}
