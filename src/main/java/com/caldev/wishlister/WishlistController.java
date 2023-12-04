package com.caldev.wishlister;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishlists")
public class WishlistController {

    @GetMapping("/{wishlistId}")
    public ResponseEntity<String> findWishlistById(@PathVariable Long wishlistId){
        return ResponseEntity.notFound().build();
    }
}
