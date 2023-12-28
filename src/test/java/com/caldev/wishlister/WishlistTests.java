package com.caldev.wishlister;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WishlistTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void returnWishlistWhenUserIsOwner() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("Bob123", "abc123")
                .getForEntity("/wishlists/9", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    void unauthorizedUserCannotCreateWishlist(){
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("fright456","rab321")
                .getForEntity("/wishlists/1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
    @Test
    void returnNoWishlistForNonOwnerAuthenticatedUser(){
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("Alice456", "xyz789")
                .getForEntity("/wishlists/9", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
    @Test
    void return404IfWishlistDoesNotExist() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("Bob123", "abc123")
                .getForEntity("/wishlists/1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
