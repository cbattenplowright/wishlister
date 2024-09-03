package com.caldev.wishlister.exceptions;

public class WishlistProductsNotFoundException extends RuntimeException{

    public WishlistProductsNotFoundException(String message) {
        super(message);
    }
}
