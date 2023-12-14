package com.caldev.wishlister.components;

import com.caldev.wishlister.models.User;
import com.caldev.wishlister.models.Wishlist;
import com.caldev.wishlister.repositories.UserRepository;
import com.caldev.wishlister.repositories.WishlistRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class DataLoader implements ApplicationRunner {
    public final UserRepository userRepository;
    public final WishlistRepository wishlistRepository;
    public final PasswordEncoder encoder;

    public DataLoader(UserRepository userRepository, WishlistRepository wishlistRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.wishlistRepository = wishlistRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.count() == 0) {
            List<User> users = Arrays.asList(
                    new User ("Bob123", encoder.encode("abc123"), "Bob", "bob@gmail.com", LocalDate.of(1973, 12, 10)),
                    new User ("Alice456", encoder.encode("xyz789"), "Alice", "alice@gmail.com", LocalDate.of(2001, 4, 28)),
                    new User ("Charlie789", encoder.encode("def456"),"Charlie", "charlie@gmail.com", LocalDate.of(1995,11,3)),
                    new User ("David012", encoder.encode("ghi789"),"David", "david@gmail.com", LocalDate.of(1998,12,5))
            );

            userRepository.saveAll(users);
        }
        if (wishlistRepository.count() == 0) {
            Optional<User> user1 = userRepository.findByUsername("Bob123");
            if (user1.isPresent()) {
                List<Wishlist> wishlists = Arrays.asList(
                        new Wishlist("Tech Gadgets", user1.get()),
                        new Wishlist("Books", user1.get()),
                        new Wishlist("Christmas List", user1.get())
                );

                wishlistRepository.saveAll(wishlists);
            }
        }
    }
}
