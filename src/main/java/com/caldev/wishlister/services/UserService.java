package com.caldev.wishlister.services;

import com.caldev.wishlister.models.Role;
import com.caldev.wishlister.models.User;
import com.caldev.wishlister.repositories.RoleRepository;
import com.caldev.wishlister.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
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

    public Optional<User> findUserById(UUID userId) {
        Optional<User> foundUser = userRepository.findByUserId(userId);
        return foundUser.filter(user -> isAuthenticatedToViewUserDetails(user, userId));
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
        } else return roles.contains(userRole) && (user.getUserId() == requestedUserId);
    }

//    public UUID retrieveAuthenticatedUserId(Principal principal) {
//        return principal.
//    }


    public boolean isAuthenticatedToViewUserDetails(UUID requestedUserId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            User authenticatedUser = (User) authentication.getPrincipal();

            Set<Role> roles = authenticatedUser.getRoles();
            Role adminRole = roleRepository.getReferenceById(2L);
            Role userRole = roleRepository.getReferenceById(1L);

            if (roles.contains(adminRole)) {
                return true; // Admin has authorization to view user details
            } else if (roles.contains(userRole) && (authenticatedUser.getUserId() == requestedUserId)) {
                return true; // User has authorization to view their own details
            }
        }

        return false; // User does not have authorization to view user details
    }
}
