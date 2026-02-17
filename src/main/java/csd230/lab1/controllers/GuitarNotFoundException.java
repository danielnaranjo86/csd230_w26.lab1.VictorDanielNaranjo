package csd230.lab1.controllers;

public class GuitarNotFoundException extends RuntimeException {
    public GuitarNotFoundException(Long id) {
        super("Could not find guitar with ID: " + id);
    }
}
