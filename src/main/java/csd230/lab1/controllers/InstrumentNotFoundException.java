package csd230.lab1.controllers;

public class InstrumentNotFoundException extends RuntimeException {
    public InstrumentNotFoundException(Long id) {
        super("Could not find instrument with ID: " + id);
    }
}
