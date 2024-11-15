package com.caldev.wishlister.exceptions;

public class SharedWishlistsNotFoundException extends RuntimeException {
    public SharedWishlistsNotFoundException(String message) {
        super(message);
    }
}
