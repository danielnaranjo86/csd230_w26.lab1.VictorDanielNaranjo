package csd230.lab1.entities;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "cart_entity")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // Lab 3 Step 2.1: Cart belongs to a User (OneToOne)
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    // LinkedHashSet for NO duplicate items
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "cart_products",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<ProductEntity> products = new LinkedHashSet<>();

    public void addProduct(ProductEntity product) {
        this.products.add(product);
        product.getCarts().add(this); // Maintain the link on both sides
    }

    public void removeProduct(ProductEntity product) {
        this.products.remove(product);
        product.getCarts().remove(this);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }

    public Set<ProductEntity> getProducts() { return products; }
    public void setProducts(Set<ProductEntity> products) { this.products = products; }
}
