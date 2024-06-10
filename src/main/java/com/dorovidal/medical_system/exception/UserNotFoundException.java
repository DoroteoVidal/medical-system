package com.dorovidal.medical_system.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super("User not found or already inactive");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
