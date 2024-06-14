package com.dorovidal.medical_system.exception;

public class AppointmentNotFoundException extends Exception {

    public AppointmentNotFoundException() {
        super("Appointment not found");
    }

    public AppointmentNotFoundException(String message) {
        super(message);
    }
}
