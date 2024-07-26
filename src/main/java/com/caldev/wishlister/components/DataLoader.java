package com.caldev.wishlister.components;

import com.caldev.wishlister.entities.Authority;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.repositories.AuthorityRepository;
import com.caldev.wishlister.repositories.UserManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {

    private final AuthorityRepository authorityRepository;
    private final UserManagementRepository userManagementRepository;
    private final PasswordEncoder passwordEncoder;


    public DataLoader(AuthorityRepository authorityRepository, UserManagementRepository userManagementRepository, PasswordEncoder passwordEncoder) {
        this.authorityRepository = authorityRepository;
        this.userManagementRepository = userManagementRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(authorityRepository.count() == 0){
            List<Authority> authorities = Arrays.asList(new Authority("ROLE_ADMIN", null), new Authority("ROLE_USER", null));

            authorityRepository.saveAll(authorities);
        }

        if(userManagementRepository.count() == 0){
            Authority adminAuthority = authorityRepository.findByAuthority("ROLE_ADMIN");
            Authority userAuthority = authorityRepository.findByAuthority("ROLE_USER");
            Set<Authority> adminAuthorities = new HashSet<>(List.of(adminAuthority, userAuthority));
            Set<Authority> userAuthorities = new HashSet<>(List.of(userAuthority));

            UserAccount userAccount1 = new UserAccount(
                    "admin@email.com",
                    passwordEncoder.encode("password"),
                    "admin",
                    LocalDate.of(2000, 1, 1),
                    adminAuthorities
                    );
            UserAccount userAccount2 = new UserAccount(
                    "alice@email.com",
                    passwordEncoder.encode("password"),
                    "Alice",
                    LocalDate.of(2000, 1, 1),
                    userAuthorities);
            UserAccount userAccount3 = new UserAccount(
                    "bob@email.com",
                    passwordEncoder.encode("password"),
                    "Bob",
                    LocalDate.of(2000, 1, 1),
                    userAuthorities);

            userManagementRepository.saveAll(Arrays.asList(userAccount1, userAccount2, userAccount3));
        }

        if(userManagementRepository.count() != 0 && authorityRepository.count() != 0){
            System.out.println("Data loaded");
        }
    }
}
