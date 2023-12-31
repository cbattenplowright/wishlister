package com.caldev.wishlister.services;

import com.caldev.wishlister.models.UserEntity;
import com.caldev.wishlister.models.SecurityUserDetails;
import com.caldev.wishlister.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> user = userRepository.findByUsername(username);

        if (!user.isPresent()){
            throw new UsernameNotFoundException("Could not find user");
        }

        return new SecurityUserDetails(user.get());
    }
}
