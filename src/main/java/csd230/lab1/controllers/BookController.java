package csd230.lab1.controllers;
import csd230.lab1.entities.BookEntity;
import csd230.lab1.entities.CartEntity;
import csd230.lab1.repositories.BookEntityRepository;
import csd230.lab1.repositories.CartEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookEntityRepository bookRepository;
    @Autowired
    private CartEntityRepository cartRepository;
    @GetMapping
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "bookList"; // Looks for bookList.html in templates
    }
    @GetMapping("/{id}")
    public String getBookById(@PathVariable Long id, Model model) {
        BookEntity book = bookRepository.findById(id).orElse(null);
        model.addAttribute("book", book);
        return "bookDetails";
    }
    @GetMapping("/add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new BookEntity());
        return "addBook";
    }
    @PostMapping("/add")
    public String addBook(@ModelAttribute BookEntity book) {
        bookRepository.save(book);
        return "redirect:/books";
    }
    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable Long id, Model model) {
        BookEntity book = bookRepository.findById(id).orElse(null);
        model.addAttribute("book", book);
        return "editBook";
    }
    @PostMapping("/edit/{id}")
    public String editBook(@PathVariable Long id, @ModelAttribute BookEntity updatedBook) {
        BookEntity existingBook = bookRepository.findById(id).orElse(null);
        if (existingBook != null) {
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setPrice(updatedBook.getPrice());
            existingBook.setCopies(updatedBook.getCopies());
            bookRepository.save(existingBook);
        }
        return "redirect:/books";
    }
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        BookEntity book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            // Remove this book from any Carts that contain it before deleting
            for (CartEntity cart : book.getCarts()) {
                cart.getProducts().remove(book);
                cartRepository.save(cart);
            }
            bookRepository.deleteById(id);
        }
        return "redirect:/books";
    }
}
