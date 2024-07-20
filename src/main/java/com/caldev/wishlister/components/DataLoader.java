package com.caldev.wishlister.components;

//import com.caldev.wishlister.entities.Role;
import com.caldev.wishlister.entities.UserAccount;
//import com.caldev.wishlister.repositories.RoleRepository;
import com.caldev.wishlister.repositories.UserManagementRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class DataLoader implements ApplicationRunner {

//    private final RoleRepository roleRepository;
    private final UserManagementRepository userManagementRepository;

    public DataLoader(UserManagementRepository userManagementRepository) {
//        this.roleRepository = roleRepository;
        this.userManagementRepository = userManagementRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        if(roleRepository.count() == 0){
//            List<Role> roles = Arrays.asList(new Role("ROLE_ADMIN", null), new Role("ROLE_USER", null));
//
//            roleRepository.saveAll(roles);
//        }

        if(userManagementRepository.count() != 0
//                && roleRepository.count() != 0
        ){
            System.out.println("Data loaded");
        }
    }
}
