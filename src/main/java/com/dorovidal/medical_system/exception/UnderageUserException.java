package com.dorovidal.medical_system.exception;

public class UnderageUserException extends Exception {

    public UnderageUserException() {
        super("The user you wish to register is a minor");
    }

    public UnderageUserException(String message) {
        super(message);
    }
}
