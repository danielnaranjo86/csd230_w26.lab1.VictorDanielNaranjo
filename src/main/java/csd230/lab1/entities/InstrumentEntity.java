package csd230.lab1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public abstract class InstrumentEntity extends ProductEntity {

    @Column(name = "brand")
    private String brand;

    @Column(name = "instrument_price")
    private double price;

    public InstrumentEntity() {}

    public InstrumentEntity(String brand, double price) {
        this.brand = brand;
        this.price = price;
    }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    @Override
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
