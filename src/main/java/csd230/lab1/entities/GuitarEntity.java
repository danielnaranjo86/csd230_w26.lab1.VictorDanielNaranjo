package csd230.lab1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("GUITAR")
public class GuitarEntity extends InstrumentEntity {

    @Column(name = "num_strings")
    private int numberOfStrings;

    public GuitarEntity() {}

    public GuitarEntity(String brand, double price, int numberOfStrings) {
        super(brand, price);
        this.numberOfStrings = numberOfStrings;
    }

    public int getNumberOfStrings() { return numberOfStrings; }
    public void setNumberOfStrings(int numberOfStrings) {
        this.numberOfStrings = numberOfStrings;
    }

    @Override
    public void sellItem() {
        System.out.println(
                "Selling Guitar: " + getBrand() +
                        " (" + numberOfStrings + " strings) for $" + getPrice()
        );
    }

    @Override
    public String toString() {
        return "Guitar{brand='" + getBrand() +
                "', strings=" + numberOfStrings +
                ", price=" + getPrice() + "}";
    }
}
