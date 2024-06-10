package com.dorovidal.medical_system.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super("There is no user with this id, try again !!");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
