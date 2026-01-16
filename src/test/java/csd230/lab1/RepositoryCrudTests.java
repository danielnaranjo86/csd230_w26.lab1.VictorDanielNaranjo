package csd230.lab1;

import csd230.lab1.entities.*;
import csd230.lab1.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)  // <-- THIS is the key
class RepositoryCrudTests {

    @Autowired BookEntityRepository bookRepo;
    @Autowired TicketEntityRepository ticketRepo;
    @Autowired MagazineEntityRepository magazineRepo;
    @Autowired DiscMagEntityRepository discMagRepo;
    @Autowired CartEntityRepository cartRepo;
    @Autowired ProductEntityRepository productRepo;

    @Test
    void bookCrud() {
        BookEntity b = new BookEntity("Test Book", 19.99, 3, "Test Author");
        b = bookRepo.save(b);

        BookEntity found = bookRepo.findById(b.getId()).orElseThrow();
        assertThat(found.getAuthor()).isEqualTo("Test Author");

        found.setAuthor("Updated Author");
        bookRepo.save(found);
        assertThat(bookRepo.findById(b.getId()).orElseThrow().getAuthor()).isEqualTo("Updated Author");

        bookRepo.deleteById(b.getId());
        assertThat(bookRepo.findById(b.getId())).isEmpty();
    }

    @Test
    void ticketCrudAndQueries() {
        TicketEntity t = new TicketEntity("Concert ticket", 25.00);
        t = ticketRepo.save(t);

        assertThat(ticketRepo.findById(t.getId())).isPresent();
        assertThat(ticketRepo.findByDescriptionContainingIgnoreCase("concert")).isNotEmpty();
        assertThat(ticketRepo.findByPriceBetween(10, 30)).isNotEmpty();

        ticketRepo.deleteById(t.getId());
        assertThat(ticketRepo.findById(t.getId())).isEmpty();
    }

    @Test
    void magazineCrudAndQueries() {
        MagazineEntity m = new MagazineEntity("Tech Monthly", 12.99, 20, 50, LocalDateTime.now());
        m = magazineRepo.save(m);

        assertThat(magazineRepo.findByTitleContainingIgnoreCase("tech")).isNotEmpty();
        assertThat(magazineRepo.findByOrderQtyGreaterThan(10)).isNotEmpty();

        magazineRepo.deleteById(m.getId());
        assertThat(magazineRepo.findById(m.getId())).isEmpty();
    }

    @Test
    void manyToMany_cartContainsProducts() {
        BookEntity b = bookRepo.save(new BookEntity("Cart Book", 10.00, 2, "Author"));
        TicketEntity t = ticketRepo.save(new TicketEntity("Cart Ticket", 15.00));

        CartEntity cart = new CartEntity();
        cart = cartRepo.save(cart);

        cart.addProduct(b);
        cart.addProduct(t);
        cartRepo.save(cart);

        CartEntity reloaded = cartRepo.findById(cart.getId()).orElseThrow();
        assertThat(reloaded.getProducts()).hasSize(2);

        // also proves ProductEntityRepository can read polymorphic table
        assertThat(productRepo.findAll()).isNotEmpty();
    }

    @Test
    void discMagCrudAndQueries() {
        DiscMagEntity d = new DiscMagEntity(
                "Disc Magazine",
                9.99,
                10,
                25,
                LocalDateTime.now(),
                true
        );

        d = discMagRepo.save(d);
        assertThat(discMagRepo.findById(d.getId())).isPresent();

        DiscMagEntity found = discMagRepo.findById(d.getId()).orElseThrow();
        assertThat(found.isHasDisc()).isTrue();

        discMagRepo.deleteById(d.getId());
        assertThat(discMagRepo.findById(d.getId())).isEmpty();
    }

}
