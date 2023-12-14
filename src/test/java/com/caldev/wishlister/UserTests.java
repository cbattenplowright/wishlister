package com.caldev.wishlister;

import com.caldev.wishlister.models.User;
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
    void shouldReturnUser() {
        ResponseEntity<User> response = restTemplate
                .withBasicAuth("Bob123", "abc123")
                .getForEntity("/users/Bob123", User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("Bob");
    }
}
