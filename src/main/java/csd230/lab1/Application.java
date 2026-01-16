package csd230.lab1;

import com.github.javafaker.Faker;
import csd230.lab1.entities.*;
import csd230.lab1.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private final ProductEntityRepository productRepo;
    private final CartEntityRepository cartRepo;
    private final BookEntityRepository bookRepo;
    private final TicketEntityRepository ticketRepo;
    private final GuitarEntityRepository guitarRepo;
    private final DrumKitEntityRepository drumKitRepo;

    public Application(ProductEntityRepository productRepo,
                       CartEntityRepository cartRepo,
                       BookEntityRepository bookRepo,
                       TicketEntityRepository ticketRepo, GuitarEntityRepository guitarRepo, DrumKitEntityRepository drumKitRepo) {
        this.productRepo = productRepo;
        this.cartRepo = cartRepo;
        this.bookRepo = bookRepo;
        this.ticketRepo = ticketRepo;
        this.guitarRepo = guitarRepo;
        this.drumKitRepo = drumKitRepo;
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) {

        Faker faker = new Faker();

        // -------------------------
        // CREATE
        // -------------------------
        BookEntity book1 = new BookEntity(
                faker.book().title(),
                Double.parseDouble(faker.commerce().price()),
                10,
                faker.book().author()
        );

        BookEntity book2 = new BookEntity(
                faker.book().title(),
                Double.parseDouble(faker.commerce().price()),
                5,
                faker.book().author()
        );

        book1 = bookRepo.save(book1);
        book2 = bookRepo.save(book2);

        CartEntity cart = new CartEntity();
        cart = cartRepo.save(cart);

        cart.addProduct(book1);
        cart.addProduct(book2);
        cartRepo.save(cart);

        TicketEntity ticket = new TicketEntity(
                faker.lorem().sentence(3),
                Double.parseDouble(faker.commerce().price())
        );
        ticket = ticketRepo.save(ticket);

        cart.addProduct(ticket);
        cartRepo.save(cart);

        GuitarEntity guitar = new GuitarEntity(
                faker.company().name(),
                faker.number().randomDouble(2, 300, 2500),
                6
        );

        DrumKitEntity drums = new DrumKitEntity(
                faker.company().name(),
                faker.number().randomDouble(2, 500, 5000),
                5
        );

        guitar = guitarRepo.save(guitar);
        drums = drumKitRepo.save(drums);

        cart.addProduct(guitar);
        cart.addProduct(drums);
        cartRepo.save(cart);

        System.out.println("\n--- MUSICAL INSTRUMENTS ---");
        guitar.sellItem();
        drums.sellItem();

        // -------------------------
        // READ
        // -------------------------
        System.out.println("\n--- ALL PRODUCTS (ProductEntityRepository.findAll) ---");
        List<ProductEntity> allProducts = productRepo.findAll();
        allProducts.forEach(System.out::println);

        System.out.println("\n--- ALL CARTS + PRODUCTS ---");
        cartRepo.findAll().forEach(c -> {
            System.out.println("Cart id=" + c.getId());
            c.getProducts().forEach(p -> System.out.println("  -> " + p));
        });

        System.out.println("\n--- TICKETS LIKE '%the%' ---");
        ticketRepo.findByDescriptionLike("%the%").forEach(System.out::println);

        System.out.println("\n--- TICKETS price between 10 and 30 ---");
        ticketRepo.findByPriceBetween(10, 30).forEach(System.out::println);

        // -------------------------
        // UPDATE
        // -------------------------
        System.out.println("\n--- UPDATE book1 author ---");
        BookEntity toUpdate = bookRepo.findById(book1.getId()).orElseThrow();
        toUpdate.setAuthor(toUpdate.getAuthor() + " (Updated)");
        bookRepo.save(toUpdate);

        System.out.println("Updated book1: " + bookRepo.findById(book1.getId()).orElseThrow());

        // -------------------------
        // DELETE
        // -------------------------
        System.out.println("\n--- DELETE book2 ---");

        // remove from carts first (join table)
        CartEntity managedCart = cartRepo.findById(cart.getId()).orElseThrow();
        managedCart.removeProduct(book2);
        cartRepo.save(managedCart);

        // now delete the product
        bookRepo.deleteById(book2.getId());


        System.out.println("\n--- PRODUCTS AFTER DELETE ---");
        productRepo.findAll().forEach(System.out::println);
    }
}
