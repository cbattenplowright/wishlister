package com.caldev.wishlister.repositoryTests;

import com.caldev.wishlister.entities.Authority;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.entities.Wishlist;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.junit.jupiter.api.Test;
import com.caldev.wishlister.repositories.WishlistRepository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WishlistRepositoryTests {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private TestEntityManager entityManager;

    private UserAccount testUserAccount;

    private Wishlist testWishlist;

    @BeforeEach
    public void setUp() {
        testUserAccount = new UserAccount("john@email.com", "password", "name", LocalDate.of(2022, 1, 1), null);
        testWishlist = new Wishlist("testWishlist", testUserAccount, null);
    }

    @Test
    public void findWishlistNameById_ShouldReturnCorrectName() {
        // Create test data

        testUserAccount = entityManager.persist(testUserAccount);

        testWishlist.setWishlistName("Test Wishlist");
        testWishlist.setUserAccount(testUserAccount);
        testWishlist = entityManager.persist(testWishlist);
        entityManager.flush();

        // Test the method
        String foundName = wishlistRepository.findWishlistNameById(testWishlist.getWishlistId());
        System.out.println("Found wishlist name: " + foundName);

        // Verify result
        assertThat(foundName).isEqualTo("Test Wishlist");
    }

    @Test
    void findWishlistNameById_ShouldReturnNull_WhenWishlistDoesNotExist() {
        String foundName = wishlistRepository.findWishlistNameById(999L);
        assertThat(foundName).isNull();
    }
}
