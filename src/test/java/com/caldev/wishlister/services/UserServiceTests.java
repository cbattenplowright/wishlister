package com.caldev.wishlister.services;

import com.caldev.wishlister.models.UserEntity;
import com.caldev.wishlister.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testFindUserById() {

        // Arrange
        UserEntity userEntity = new UserEntity("testUsername", "testPassword", "testName", "test@email.com", LocalDate.of(2000, 1, 1));
        UUID userId = UUID.randomUUID();
        userEntity.setUserId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        // Act
        UserEntity foundUser = userService.findUserById(userId);

        // Assert
        assertThat(foundUser).isEqualTo(userEntity);
        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
    }
}
