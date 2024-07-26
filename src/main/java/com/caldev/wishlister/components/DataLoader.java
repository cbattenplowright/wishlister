package com.caldev.wishlister.components;

import com.caldev.wishlister.entities.Authority;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.repositories.AuthorityRepository;
import com.caldev.wishlister.repositories.UserRepository;
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

    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;

    public DataLoader(AuthorityRepository authorityRepository, UserRepository userRepository) {
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(authorityRepository.count() == 0){
            List<Authority> authorities = Arrays.asList(new Authority("ROLE_ADMIN", null), new Authority("ROLE_USER", null));

            authorityRepository.saveAll(authorities);
        }

        if(userRepository.count() == 0){
            Authority adminAuthority = authorityRepository.findByRoleName("ROLE_ADMIN");
            Authority userAuthority = authorityRepository.findByRoleName("ROLE_USER");
            Set<Authority> adminAuthorities = new HashSet<>(List.of(adminAuthority, userAuthority));
            Set<Authority> userAuthorities = new HashSet<>(List.of(userAuthority));

            UserAccount userAccount1 = new UserAccount(
                    "username",
                    "password",
                    "name",
                    "email@email.com",
                    LocalDate.of(2000, 1, 1),
                    adminAuthorities
                    );
            UserAccount userAccount2 = new UserAccount(
                    "username2",
                    "password2",
                    "name2",
                    "email2@email.com",
                    LocalDate.of(2000, 1, 1),
                    userAuthorities);

            userRepository.saveAll(Arrays.asList(userAccount1, userAccount2));
        }

        if(userRepository.count() != 0 && authorityRepository.count() != 0){
            System.out.println("Data loaded");
        }
    }
}
