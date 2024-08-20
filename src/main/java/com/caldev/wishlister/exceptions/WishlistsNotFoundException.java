package com.caldev.wishlister.exceptions;

public class WishlistsNotFoundException extends RuntimeException{

    public WishlistsNotFoundException(String message) {
        super(message);
    }
}
