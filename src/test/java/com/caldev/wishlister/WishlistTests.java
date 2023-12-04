package com.caldev.wishlister;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WishlistTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldReturnAWishlistWhenUserCreatesAWishlist() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("bob123", "abc123")
                .getForEntity("/wishlists/1", String.class);
        System.out.println("Response body: " + response.getStatusCode());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
