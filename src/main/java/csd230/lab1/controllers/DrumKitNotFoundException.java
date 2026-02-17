package csd230.lab1.controllers;

public class DrumKitNotFoundException extends RuntimeException {
    public DrumKitNotFoundException(Long id) { super("DrumKit not found with id " + id); }
}
