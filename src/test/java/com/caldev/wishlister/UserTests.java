package com.caldev.wishlister;

import com.caldev.wishlister.models.User;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTests {

    @Autowired
    TestRestTemplate restTemplate;

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
    void shouldReturn404IfUserDoesNotExist(){
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("David012", "ghi789")
                .getForEntity("/users/9886bb64-a584-46f0-aca4-10e3dec74688", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldReturnUserIfUserIsAdmin(){
        ResponseEntity<User> response = restTemplate
                .withBasicAuth("David012", "ghi789")
                .getForEntity("/users/9886bb64-a584-46f0-aca4-10e3dec74458", User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("Bob");
    }

    @Test
    void shouldReturnCollectionOfUsersIfAdmin(){
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("David012", "ghi789")
                .getForEntity("/users", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturn403IfUserIsNotAuthorizedToViewAllUsers(){
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("Alice456", "xyz789")
                .getForEntity("/users", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldReturn401IfUserIsNotAuthenticatedToViewAllUsers(){
        ResponseEntity<String> response = restTemplate
                .getForEntity("/users", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
