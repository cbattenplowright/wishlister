package com.caldev.wishlister.components;

import com.caldev.wishlister.entities.Role;
import com.caldev.wishlister.entities.User;
import com.caldev.wishlister.repositories.RoleRepository;
import com.caldev.wishlister.repositories.UserManagementRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {

    private final RoleRepository roleRepository;
    private final UserManagementRepository userManagementRepository;

    public DataLoader(RoleRepository roleRepository, UserManagementRepository userManagementRepository) {
        this.roleRepository = roleRepository;
        this.userManagementRepository = userManagementRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(roleRepository.count() == 0){
            List<Role> roles = Arrays.asList(new Role("ROLE_ADMIN", null), new Role("ROLE_USER", null));

            roleRepository.saveAll(roles);
        }

        if(userManagementRepository.count() == 0){
            Role adminRole = roleRepository.findByRoleName("ROLE_ADMIN");
            Role userRole = roleRepository.findByRoleName("ROLE_USER");
            Set<Role> adminRoles = new HashSet<>(List.of(adminRole, userRole));
            Set<Role> userRoles = new HashSet<>(List.of(userRole));

            User user1 = new User(
                    "username",
                    "password",
                    "name",
                    "email@email.com",
                    LocalDate.of(2000, 1, 1),
                    adminRoles
                    );
            User user2 = new User(
                    "username2",
                    "password2",
                    "name2",
                    "email2@email.com",
                    LocalDate.of(2000, 1, 1),
                    userRoles);

            userManagementRepository.saveAll(Arrays.asList(user1, user2));
        }

        if(userManagementRepository.count() != 0 && roleRepository.count() != 0){
            System.out.println("Data loaded");
        }
    }
}
