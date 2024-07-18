package com.caldev.wishlister.configurations;

import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.repositories.UserManagementRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

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
        // Users must be authenticated to enter the site. If not they are directed to the login page.
        //Only the lists and products an authenticated user has added or had shared with them should be visible to them
        //Only admins can view all users, lists, products
        //Only authenticated users can delete their lists, products and account
        //Only admins can delete any users, lists, products
        //Rules should apply to both CMD line and HTML tools

        http.authorizeHttpRequests(auth -> auth
                .anyRequest().denyAll()
                .anyRequest().authenticated()).httpBasic(withDefaults());
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
