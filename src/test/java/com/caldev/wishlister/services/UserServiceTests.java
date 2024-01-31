package com.caldev.wishlister.services;

import com.caldev.wishlister.enums.RoleName;
import com.caldev.wishlister.models.Role;
import com.caldev.wishlister.models.SecurityUserDetails;
import com.caldev.wishlister.models.UserEntity;
import com.caldev.wishlister.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

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

    @Test
    public void testIsAuthorizedToAccessUserDetails_Admin() {
        // Arrange
        Set<Role> roles = new HashSet<>();
        Role userRole = new Role(RoleName.ROLE_USER);
        userRole.setRoleId(1L);
        Role adminRole = new Role(RoleName.ROLE_ADMIN);
        adminRole.setRoleId(2L);
        roles.add(adminRole);
        roles.add(userRole);


        SecurityUserDetails userDetails = mock(SecurityUserDetails.class);
        UserEntity userEntity = mock(UserEntity.class);
        when(userEntity.getRoles()).thenReturn(roles);
        when(userDetails.getUser()).thenReturn(userEntity);

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        boolean authorized = userService.isAuthorizedToAccessUserDetails(UUID.randomUUID());

        // Assert
        assertTrue(authorized);

        verify(userEntity, Mockito.times(1)).getRoles();
        verify(userDetails, Mockito.times(1)).getUser();
        verify(authentication, Mockito.times(1)).isAuthenticated();
        verify(authentication, Mockito.times(1)).getPrincipal();
        verify(securityContext, Mockito.times(1)).getAuthentication();
    }

    @Test
    public void testIsAuthorizedToAccessUserDetails_User() {
        // Write test to access user details if user

        //Arrange
        Set<Role> roles = new HashSet<>();
        Role userRole = new Role(RoleName.ROLE_USER);
        userRole.setRoleId(1L);
        roles.add(userRole);

        SecurityUserDetails userDetails = mock(SecurityUserDetails.class);
        UserEntity userEntity = mock(UserEntity.class);
        when(userEntity.getUserId()).thenReturn(UUID.randomUUID());
        when(userEntity.getRoles()).thenReturn(roles);
        when(userDetails.getUser()).thenReturn(userEntity);

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        boolean isAuthorized = userService.isAuthorizedToAccessUserDetails(userEntity.getUserId());

        //Assert
        assertTrue(isAuthorized);

        verify(userEntity, Mockito.times(2)).getUserId();
        verify(userEntity, Mockito.times(1)).getRoles();
        verify(authentication, Mockito.times(1)).getPrincipal();
        verify(securityContext, Mockito.times(1)).getAuthentication();
    }

    @Test
    public void testIsAuthorizedToAccessUserDetails_NotAuthenticated() {
        // Write test to access user details if user is not authenticated

        //Arrange
        SecurityUserDetails userDetails = mock(SecurityUserDetails.class);
        UserEntity userEntity = mock(UserEntity.class);
        when(userEntity.getUserId()).thenReturn(UUID.randomUUID());

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        boolean isAuthorized = userService.isAuthorizedToAccessUserDetails(userEntity.getUserId());

        // Assert
        assertFalse(isAuthorized);

        verify(userEntity, Mockito.times(1)).getUserId();
        verify(userDetails, Mockito.times(0)).getUser();
    }
}
