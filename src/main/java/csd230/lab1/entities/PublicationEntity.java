package csd230.lab1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

/**
 * Abstract parent for all publications (Books, Magazines, DiscMag, etc.)
 * Uses SINGLE_TABLE inheritance via ProductEntity
 */
@Entity
public abstract class PublicationEntity extends ProductEntity {

    @Column(nullable = true)
    private String title;

    @Column(name = "pub_price", nullable = true)
    private double price;

    @Column(nullable = true)
    private int copies;

    protected PublicationEntity() {
        // Required by JPA
    }

    protected PublicationEntity(String title, double price, int copies) {
        this.title = title;
        this.price = price;
        this.copies = copies;
    }

    /**
     * Default sell behavior for publications:
     * - Decrease stock if available
     * - Prevent selling when out of stock
     */
    @Override
    public void sellItem() {
        if (copies > 0) {
            copies--;
            System.out.println(
                    "Sold publication: '" + title + "' | Remaining copies: " + copies
            );
        } else {
            System.out.println(
                    "Cannot sell publication: '" + title + "' (Out of stock)"
            );
        }
    }

    @Override
    public double getPrice() {
        return price;
    }

    /* ======================
       Getters and Setters
       ====================== */

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    /* ======================
       Utility Methods
       ====================== */

    @Override
    public String toString() {
        return "Publication{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", copies=" + copies +
                '}';
    }
}
