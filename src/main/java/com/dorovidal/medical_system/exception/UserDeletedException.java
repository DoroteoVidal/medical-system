package com.dorovidal.medical_system.exception;

public class UserDeletedException extends Exception {

    public UserDeletedException() {
        super("The user has been deleted");
    }

    public UserDeletedException(String message) {
        super(message);
    }
}
