package com.dorovidal.medical_system.exception;

public class UserFoundException extends Exception {

    public UserFoundException() {
        super("User with this email already exists, try again !!");
    }

    public UserFoundException(String message) {
        super(message);
    }
}
