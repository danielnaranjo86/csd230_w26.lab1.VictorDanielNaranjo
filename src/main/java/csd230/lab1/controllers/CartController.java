package csd230.lab1.controllers;

import csd230.lab1.entities.*;
import csd230.lab1.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Set;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartEntityRepository cartRepository;

    @Autowired
    private BookEntityRepository bookRepository;

    @Autowired
    private UserEntityRepository userRepository;

    @Autowired
    private ProductEntityRepository productRepository;

    @Autowired
    private OrderEntityRepository orderRepository;

    // Helper method to get the current user's cart (Lab 3 Step 2.3)
    private CartEntity getCartForCurrentUser(Principal principal) {
        String username = principal.getName();
        UserEntity user = userRepository.findByUsername(username);

        CartEntity cart = cartRepository.findByUser(user);

        if (cart == null) {
            cart = new CartEntity();
            cart.setUser(user);
            cartRepository.save(cart);
        }

        return cart;
    }

    @GetMapping
    public String viewCart(Model model, Principal principal) {
        CartEntity cart = getCartForCurrentUser(principal);
        model.addAttribute("cart", cart);
        return "cartDetails";
    }

    @GetMapping("/add/{bookId}")
    public String addToCart(@PathVariable Long bookId, Principal principal) {
        CartEntity cart = getCartForCurrentUser(principal);
        BookEntity book = bookRepository.findById(bookId).orElse(null);

        if (book != null) {
            cart.addProduct(book);
            cartRepository.save(cart);
        }

        return "redirect:/books";
    }

    @GetMapping("/remove/{bookId}")
    public String removeFromCart(@PathVariable Long bookId, Principal principal) {
        CartEntity cart = getCartForCurrentUser(principal);
        BookEntity book = bookRepository.findById(bookId).orElse(null);

        if (book != null) {
            cart.getProducts().remove(book);
            cartRepository.save(cart);
        }

        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    @Transactional
    public String checkout(Principal principal) {

        CartEntity cart = getCartForCurrentUser(principal);

        if (cart == null || cart.getProducts() == null || cart.getProducts().isEmpty()) {
            return "redirect:/cart";
        }

        OrderEntity order = new OrderEntity();
        order.setOrderDate(LocalDateTime.now());

        double total = 0.0;

        // iterate over a copy to avoid ConcurrentModification if you remove/clear
        for (ProductEntity product : Set.copyOf(cart.getProducts())) {
            total += product.getPrice();

            // decrement copies for publications
            if (product instanceof PublicationEntity pub) {
                if (pub.getCopies() <= 0) {
                    // skip out-of-stock
                    continue;
                }
                pub.setCopies(pub.getCopies() - 1);
                productRepository.save(pub);
            }

            order.addProduct(product);
        }

        order.setTotalAmount(total);

        // clear cart
        cart.getProducts().clear();
        cartRepository.save(cart);

        // save order
        orderRepository.save(order);

        // IMPORTANT: make sure you actually have a GET mapping for /orders/{id}
        return "redirect:/orders/" + order.getId();
    }


}
