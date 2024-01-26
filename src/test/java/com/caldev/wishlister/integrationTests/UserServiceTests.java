package com.caldev.wishlister.integrationTests;

import com.caldev.wishlister.models.UserEntity;
import com.caldev.wishlister.models.UserEntityDTO;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Nested
    class showUserTestsEntity {
        @Test
        void shouldReturnUserIfUserIsAuthenticatedAndOwner() {
            ResponseEntity<UserEntity> response = restTemplate
                    .withBasicAuth("Bob123", "abc123")
                    .getForEntity("/users/9886bb64-a584-46f0-aca4-10e3dec74458", UserEntity.class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody().getName()).isEqualTo("Bob");
        }

        @Test
        void shouldReturn403IfUserIsAuthenticatedAndNotOwner() {
            ResponseEntity<String> response = restTemplate
                    .withBasicAuth("Alice456", "xyz789")
                    .getForEntity("/users/9886bb64-a584-46f0-aca4-10e3dec74458", String.class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        }

        @Test
        void shouldReturn404IfUserDoesNotExist() {
            ResponseEntity<String> response = restTemplate
                    .withBasicAuth("David012", "ghi789")
                    .getForEntity("/users/9886bb64-a584-46f0-aca4-10e3dec74688", String.class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }

        @Test
        void shouldReturnUserIfUserIsAdmin() {
            ResponseEntity<UserEntity> response = restTemplate
                    .withBasicAuth("David012", "ghi789")
                    .getForEntity("/users/9886bb64-a584-46f0-aca4-10e3dec74458", UserEntity.class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody().getName()).isEqualTo("Bob");
        }
    }

    @Nested
    class indexUserTestsEntity {
        @Test
        void shouldReturnCollectionOfUsersIfAdmin() {
            ResponseEntity<String> response = restTemplate
                    .withBasicAuth("David012", "ghi789")
                    .getForEntity("/users", String.class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }

        @Test
        void shouldReturn403IfUserIsNotAuthorizedToViewAllUsers() {
            ResponseEntity<String> response = restTemplate
                    .withBasicAuth("Alice456", "xyz789")
                    .getForEntity("/users", String.class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        }

        @Test
        void shouldReturn401IfUserIsNotAuthenticatedToViewAllUsers() {
            ResponseEntity<String> response = restTemplate
                    .getForEntity("/users", String.class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        }
    }

    @Nested
    class createUserTestsEntity {
        @Test
        void shouldReturn201WhenUserIsCreated() {

            HttpEntity<UserEntityDTO> createUserResponseBody = new HttpEntity<>(
                    new UserEntityDTO("Flo329", "password", "Flo", "flo@gmail.com", LocalDate.of(1990, 1, 1)));

            ResponseEntity<UserEntity> createUserResponse = restTemplate
                    .postForEntity("/users",
                            createUserResponseBody,
                            UserEntity.class);
            assertThat(createUserResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

            UUID userId = createUserResponse.getBody().getUserId();

            ResponseEntity<UserEntity> deleteUserResponse = restTemplate
                    .withBasicAuth("Flo329", "password")
                    .exchange("/users/" + userId, HttpMethod.DELETE, null, UserEntity.class);
            assertThat(deleteUserResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }

    @Nested
    class deleteUserTestsEntity {

        @Test
        void shouldReturn200WhenUserIsDeleted() {

            HttpEntity<UserEntityDTO> createUserResponseBody = new HttpEntity<>(
                    new UserEntityDTO("Flo329", "password", "Flo", "flo@gmail.com", LocalDate.of(1990, 1, 1)));

            ResponseEntity<UserEntity> createUserResponse = restTemplate
                    .postForEntity("/users",
                            createUserResponseBody,
                            UserEntity.class);
            assertThat(createUserResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

            UUID userId = createUserResponse.getBody().getUserId();

            // Generate code to delete user after test
            ResponseEntity<String> deleteUserResponse = restTemplate
                    .withBasicAuth("Flo329", "password")
                    .exchange("/users/" + userId,
                            HttpMethod.DELETE,
                            null,
                            String.class);
            assertThat(deleteUserResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }
}