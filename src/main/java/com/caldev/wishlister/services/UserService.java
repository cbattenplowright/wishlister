package com.caldev.wishlister.services;

import com.caldev.wishlister.models.Role;
import com.caldev.wishlister.models.SecurityUserDetails;
import com.caldev.wishlister.models.UserEntity;
import com.caldev.wishlister.models.UserEntityDTO;
import com.caldev.wishlister.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final RoleService roleService;

    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    // Work out how you will send back the User or ResponseEntity codes!!

    public UserEntity findUserById(UUID userId) {

        Optional<UserEntity> foundUser = userRepository.findById(userId);
        return foundUser.orElse(null);
    }

    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public boolean isAuthorizedToViewUserDetails(UUID requestedUserId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            SecurityUserDetails authenticatedUser = (SecurityUserDetails) authentication.getPrincipal();
            UserEntity userEntity = authenticatedUser.getUser();

            Set<Role> roles = userEntity.getRoles();
            long adminId = 2;
            long userId = 1;

            if (roles.stream().anyMatch(role -> role.getRoleId() == adminId) ||
                    (roles.stream().anyMatch(role -> role.getRoleId() == userId) && userEntity.getUserId().equals(requestedUserId))) {
                return true; // Admin is authorized to view any user details, User is authorized to view their own user details
            }
        }
        return false; // User is not authorized to view user details
    }

    public UserEntity createUser(UserEntityDTO userEntityDTO) {

        // Create the user object from the userDTO that was passed in from the controller
        // Give it the default role of USER
        // Save the user to the database

        UserEntity userEntity = new UserEntity(
                userEntityDTO.getUsername(),
                userEntityDTO.getPassword(),
                userEntityDTO.getName(),
                userEntityDTO.getEmail(),
                userEntityDTO.getDateOfBirth()
        );

        return userRepository.save(userEntity);

    }

    public UUID deleteUser(UUID userId) {

        Optional<UserEntity> foundUser = userRepository.findById(userId);
        if (foundUser.isPresent()) {
            userRepository.delete(foundUser.get());
            return userId;
        } else {
            return null;
        }
    }
}
