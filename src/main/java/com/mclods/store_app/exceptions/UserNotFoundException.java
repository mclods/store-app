package com.mclods.store_app.exceptions;

public class UserNotFoundException extends Exception {
    String message;

    public UserNotFoundException(Long id) {
        message = String.format("User with id=%d not found", id);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
