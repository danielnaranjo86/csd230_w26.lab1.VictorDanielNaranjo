package csd230.lab1.entities;
import csd230.lab1.pojos.SaleableItem;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "product_type", discriminatorType = DiscriminatorType.STRING)
public abstract class ProductEntity implements Serializable, SaleableItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(mappedBy = "products")
    private Set<CartEntity> carts = new HashSet<>();
    public Set<CartEntity> getCarts() { return carts; }
    public void setCarts(Set<CartEntity> carts) { this.carts = carts; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    @Override
    public String toString() {
        return "ProductEntity{id=" + id + "} : " + super.toString();
    }
}