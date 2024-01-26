package com.caldev.wishlister.services;

import com.caldev.wishlister.models.UserEntity;
import com.caldev.wishlister.models.Wishlist;
import com.caldev.wishlister.repositories.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WishlistServiceTests {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private WishlistService wishlistService;

    @Test
    public void testFindWishlistById() {
        // Arrange
        UserEntity userEntity = new UserEntity("testUsername", "testPassword", "testName", "test@email.com", LocalDate.of(2000, 1, 1));
        Wishlist wishlist = new Wishlist("testWishlist", userEntity);
        wishlist.setWishlistId(1L);
        when(wishlistRepository.findById(1L)).thenReturn(Optional.of(wishlist));

        // Act
        Wishlist foundWishlist = wishlistService.findWishlistById(1L);

        // Assert
        assertThat(foundWishlist).isEqualTo(wishlist);
        Mockito.verify(wishlistRepository, Mockito.times(1)).findById(1L);
    }
}
