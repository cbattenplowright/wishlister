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
import java.util.List;
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

    @Test
    // TODO write test for findAlLUsers Service method
    public void testFindAllUsers() {
        // Arrange
        UserEntity userEntity1 = new UserEntity("testUsername1", "testPassword1", "testName1", "test1@email.com", LocalDate.of(2024,1,1));
        UserEntity userEntity2 = new UserEntity("testUsername2", "testPassword2", "testName2", "test2@email.com", LocalDate.of(2024,1,1));
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();

        userEntity1.setUserId(userId1);
        userEntity2.setUserId(userId2);

        when(userRepository.findAll()).thenReturn(List.of(userEntity1, userEntity2));

        // Act
        List<UserEntity> foundUsers = userService.findAllUsers();

        // Assert
        assertThat(foundUsers).isEqualTo(List.of(userEntity1, userEntity2));
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }
}
