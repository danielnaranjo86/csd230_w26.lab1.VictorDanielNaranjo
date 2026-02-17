package csd230.lab1.controllers;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException(Long id) {
        super("Could not find ticket with ID: " + id);
    }
}
