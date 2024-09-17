package com.caldev.wishlister.components;

import com.caldev.wishlister.entities.*;
import com.caldev.wishlister.repositories.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

@Component
public class DataLoader implements ApplicationRunner {

    private final AuthorityRepository authorityRepository;
    private final UserManagementRepository userManagementRepository;
    private final ProductRepository productRepository;
    private final WishlistRepository wishlistRepository;
    private final WishlistProductRepository wishlistProductRepository;
    private final PasswordEncoder passwordEncoder;


    public DataLoader(AuthorityRepository authorityRepository, UserManagementRepository userManagementRepository,ProductRepository productRepository, WishlistRepository wishlistRepository,WishlistProductRepository wishlistProductRepository, PasswordEncoder passwordEncoder) {
        this.authorityRepository = authorityRepository;
        this.userManagementRepository = userManagementRepository;
        this.productRepository = productRepository;
        this.wishlistRepository = wishlistRepository;
        this.wishlistProductRepository = wishlistProductRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (authorityRepository.count() == 0) {
            List<Authority> authorities = Arrays.asList(new Authority("ROLE_ADMIN"), new Authority("ROLE_USER"));

            authorityRepository.saveAll(authorities);
        }

        if (userManagementRepository.count() == 0) {
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

        if(productRepository.count() == 0){
            UserAccount userAccount1 = userManagementRepository.findByEmail("alice@email.com");
            UserAccount userAccount2 = userManagementRepository.findByEmail("bob@email.com");

            productRepository.saveAll(Arrays.asList(
                    new Product("testProduct",
                            1599,
                            new URL("https://www.testUrl.com"),
                            new URL("https://www.imageUrl.com"),
                            "description",
                            PrioritySelection.NON_URGENT,
                            LocalDate.now(),
                            new ArrayList<>(),
                            userAccount1),
                    new Product("testProduct2",
                            144,
                            new URL("https://www.testUrl2.com"),
                            new URL("https://www.imageUrl2.com"),
                            "description2",
                            PrioritySelection.IMPORTANT,
                            LocalDate.now(),
                            new ArrayList<>(),
                            userAccount2),
                    new Product("testProduct3",
                        1599,
                        new URL("https://www.testUrl.com"),
                        new URL("https://www.imageUrl.com"),
                        "description",
                        PrioritySelection.NON_URGENT,
                        LocalDate.now(),
                        new ArrayList<>(),
                        userAccount1)));
        }

        if (wishlistRepository.count() == 0) {
            UserAccount userAccount1 = userManagementRepository.findByEmail("alice@email.com");
            UserAccount userAccount2 = userManagementRepository.findByEmail("bob@email.com");
            wishlistRepository.saveAll(Arrays.asList(
                    new Wishlist("Wishlist 1", userAccount1, new ArrayList<>()),
                    new Wishlist("Wishlist 2", userAccount2, new ArrayList<>()),
                    new Wishlist("Wishlist 3", userAccount1, new ArrayList<>()),
                    new Wishlist("Wishlist 4", userAccount1, new ArrayList<>()),
                    new Wishlist("Wishlist 5", userAccount2, new ArrayList<>())));
        }

        if (wishlistProductRepository.count() == 0) {
            UserAccount userAccount1 = userManagementRepository.findByEmail("alice@email.com");
            UserAccount userAccount2 = userManagementRepository.findByEmail("bob@email.com");

            Product product1 = productRepository.findById(1L).get();
            Product product2 = productRepository.findById(2L).get();
            Product product3 = productRepository.findById(3L).get();

            Wishlist wishlist1 = wishlistRepository.findById(1L).get();
            Wishlist wishlist2 = wishlistRepository.findById(2L).get();
            Wishlist wishlist3 = wishlistRepository.findById(3L).get();
            Wishlist wishlist4 = wishlistRepository.findById(4L).get();
            Wishlist wishlist5 = wishlistRepository.findById(5L).get();

            wishlistProductRepository.saveAll(Arrays.asList(
                    new WishlistProduct(wishlist1, product1, false),
                    new WishlistProduct(wishlist1, product2, false),
                    new WishlistProduct(wishlist2, product1, false),
                    new WishlistProduct(wishlist2, product3, true),
                    new WishlistProduct(wishlist3, product1, false),
                    new WishlistProduct(wishlist3, product2, true),
                    new WishlistProduct(wishlist4, product1, false),
                    new WishlistProduct(wishlist4, product2, false),
                    new WishlistProduct(wishlist5, product1, true)
            ));
        }

        if (userManagementRepository.count() != 0 && authorityRepository.count() != 0 && productRepository.count() != 0 && wishlistRepository.count() != 0) {
            System.out.println("Data loaded");
        }

    }
}
