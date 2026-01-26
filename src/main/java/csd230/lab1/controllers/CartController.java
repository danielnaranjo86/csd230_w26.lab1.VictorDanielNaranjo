package csd230.lab1.controllers;

import csd230.lab1.entities.*;
import csd230.lab1.repositories.CartEntityRepository;
import csd230.lab1.repositories.OrderEntityRepository;
import csd230.lab1.repositories.ProductEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/cart")
public class CartController {

    private static final Long DEFAULT_CART_ID = 1L;

    @Autowired private CartEntityRepository cartRepository;
    @Autowired private ProductEntityRepository productRepository;
    @Autowired private OrderEntityRepository orderRepository;

    // 1. View the contents of the cart
    @GetMapping
    public String viewCart(Model model) {
        CartEntity cart = cartRepository.findAll().stream().findFirst()
                .orElseGet(() -> cartRepository.save(new CartEntity()));

        model.addAttribute("cart", cart);
        return "cartDetails";
    }

    // 2. Add ANY product (BookEntity, DrumKitEntity, etc.) to the cart
    private CartEntity getOrCreateCart() {
        return cartRepository.findAll().stream().findFirst()
                .orElseGet(() -> cartRepository.save(new CartEntity()));
    }

    @GetMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId) {
        CartEntity cart = getOrCreateCart();
        ProductEntity product = productRepository.findById(productId).orElse(null);

        if (product != null) {
            cart.addProduct(product);
            cartRepository.save(cart);
        }
        return "redirect:/cart";
    }

    // 3. Remove ANY product from cart
    @GetMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId) {
        CartEntity cart = cartRepository.findById(DEFAULT_CART_ID).orElse(null);
        ProductEntity product = productRepository.findById(productId).orElse(null);

        if (cart != null && product != null) {
            cart.removeProduct(product); // use your helper to keep both sides in sync
            cartRepository.save(cart);
        }
        return "redirect:/cart";
    }

    // 4. PART 2: Checkout
    @PostMapping("/checkout")
    @Transactional
    public String checkout() {
        CartEntity cart = cartRepository.findById(DEFAULT_CART_ID).orElse(null);

        if (cart == null || cart.getProducts() == null || cart.getProducts().isEmpty()) {
            return "redirect:/cart";
        }

        OrderEntity order = new OrderEntity();
        order.setOrderDate(LocalDateTime.now());

        double total = 0.0;

        for (ProductEntity product : cart.getProducts()) {
            total += product.getPrice();

            // If it's a Publication (Book/Magazine/etc.), decrement copies
            if (product instanceof PublicationEntity pub) {
                // Prevent going negative
                if (pub.getCopies() <= 0) {
                    // Simple behavior: skip out-of-stock items
                    // (or redirect with an error message)
                    continue;
                }
                pub.setCopies(pub.getCopies() - 1);
                productRepository.save(pub); // <-- key point: no PublicationEntityRepository needed
            }

            order.addProduct(product);
        }

        order.setTotalAmount(total);

        // clear cart
        cart.getProducts().clear();
        cartRepository.save(cart);

        // save order last (or earlier—either is fine under @Transactional)
        orderRepository.save(order);

        return "redirect:/orders/" + order.getId();
    }
}
