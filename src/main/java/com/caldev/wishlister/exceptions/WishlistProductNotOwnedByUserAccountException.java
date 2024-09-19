package com.caldev.wishlister.exceptions;

public class WishlistProductNotOwnedByUserAccountException extends RuntimeException {

    public WishlistProductNotOwnedByUserAccountException(String message) {
        super(message);
    }
}
