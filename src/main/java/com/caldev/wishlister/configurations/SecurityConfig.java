package com.caldev.wishlister.configurations;

import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.repositories.UserManagementRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        userDetailsManager.createUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build());
        userDetailsManager.createUser(User.withDefaultPasswordEncoder().username("admin").password("password").roles("ADMIN").build());
        return userDetailsManager;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/api/users").hasRole("ADMIN")
                .anyRequest().authenticated();
        return http.build();

    }

    @Bean
    CommandLineRunner initUsers(UserManagementRepository userManagementRepository) {
        return args -> {
            userManagementRepository.save(new UserAccount("user", "password", "User", "user@email.com", LocalDate.of(2000, 1, 1), new ArrayList<>(List.of(new SimpleGrantedAuthority("ROLE_USER")))));
            userManagementRepository.save(new UserAccount("admin", "password", "Admin", "admin@email.com", LocalDate.of(2000, 1, 1), new ArrayList<>(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")))));
        };
    }
}
