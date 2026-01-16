package csd230.lab1.pojos;

import java.util.List;
import java.util.Objects;

public class Cart {
    private List<Product> items;

    public Cart() {
    }

    public Cart(List<Product> items) {
        this.items = items;
    }

    public void addItem(Product item) {
        items.add(item);
    }

    public List<Product> getItems() {
        return items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart cart)) return false;
        return Objects.equals(getItems(), cart.getItems());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getItems());
    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                '}';
    }
}
