package csd230.lab1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DRUMKIT")
public class DrumKitEntity extends InstrumentEntity {

    @Column(name = "num_pieces")
    private int numberOfPieces;

    public DrumKitEntity() {}

    public DrumKitEntity(String brand, double price, int numberOfPieces) {
        super(brand, price);
        this.numberOfPieces = numberOfPieces;
    }

    public int getNumberOfPieces() { return numberOfPieces; }
    public void setNumberOfPieces(int numberOfPieces) {
        this.numberOfPieces = numberOfPieces;
    }

    @Override
    public void sellItem() {
        System.out.println(
                "Selling Drum Kit: " + getBrand() +
                        " (" + numberOfPieces + " pieces) for $" + getPrice()
        );
    }

    @Override
    public String toString() {
        return "DrumKit{brand='" + getBrand() +
                "', pieces=" + numberOfPieces +
                ", price=" + getPrice() + "}";
    }
}
