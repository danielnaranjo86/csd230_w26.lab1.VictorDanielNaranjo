package csd230.lab1.controllers;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Could not find product with ID: " + id);
    }
}
