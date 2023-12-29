package com.caldev.wishlister;

import com.caldev.wishlister.models.User;
import com.caldev.wishlister.models.UserDTO;
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
public class UserTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Nested
    class showUserTests {
        @Test
        void shouldReturnUserIfUserIsAuthenticatedAndOwner() {
            ResponseEntity<User> response = restTemplate
                    .withBasicAuth("Bob123", "abc123")
                    .getForEntity("/users/9886bb64-a584-46f0-aca4-10e3dec74458", User.class);
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
            ResponseEntity<User> response = restTemplate
                    .withBasicAuth("David012", "ghi789")
                    .getForEntity("/users/9886bb64-a584-46f0-aca4-10e3dec74458", User.class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody().getName()).isEqualTo("Bob");
        }
    }

    @Nested
    class indexUserTests {
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
    class createUserTests {
        @Test
        void shouldReturn201WhenUserIsCreated() {

            HttpEntity<UserDTO> createUserResponseBody = new HttpEntity<>(
                    new UserDTO("Flo329", "password", "Flo", "flo@gmail.com", LocalDate.of(1990, 1, 1)));

            ResponseEntity<User> createUserResponse = restTemplate
                    .postForEntity("/users",
                            createUserResponseBody,
                            User.class);
            assertThat(createUserResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

            UUID userId = createUserResponse.getBody().getUserId();

            ResponseEntity<User> deleteUserResponse = restTemplate
                    .withBasicAuth("Flo329", "password")
                    .exchange("/users/" + userId, HttpMethod.DELETE, null, User.class);
            assertThat(deleteUserResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }

    @Nested
    class deleteUserTests {

        @Test
        void shouldReturn200WhenUserIsDeleted() {

            HttpEntity<UserDTO> createUserResponseBody = new HttpEntity<>(
                    new UserDTO("Flo329", "password", "Flo", "flo@gmail.com", LocalDate.of(1990, 1, 1)));

            ResponseEntity<User> createUserResponse = restTemplate
                    .postForEntity("/users",
                            createUserResponseBody,
                            User.class);
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