package csd230.lab1.controllers;

public class MagazineNotFoundException extends RuntimeException {
    public MagazineNotFoundException(Long id) {
        String message = "Could not find magazine with ID: " + id;
    }
}
