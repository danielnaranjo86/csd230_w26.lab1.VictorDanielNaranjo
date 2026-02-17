package csd230.lab1.controllers;

import csd230.lab1.entities.ProductEntity;
import csd230.lab1.repositories.ProductEntityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product REST API", description = "JSON API for managing products")
@RestController
@RequestMapping("/api/rest/products")
@CrossOrigin(origins = "*")
public class ProductRestController {
    private final ProductEntityRepository productRepository;

    public ProductRestController(ProductEntityRepository productRepository) {this.productRepository = productRepository;}

    @Operation(summary = "Get all products as JSON")
    @GetMapping
    public List<ProductEntity> all() {return productRepository.findAll();}

    @Operation (summary = "Get a single product by ID")
    @GetMapping("/{id}")
    public ProductEntity getProduct(@PathVariable Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Operation(summary = "Create a new product")
    @PostMapping
    public ProductEntity newProduct(@RequestBody ProductEntity newProduct) {
        return productRepository.save(newProduct);
    }

    @Operation(summary = "Update or Replace a product")
    @PutMapping("/{id}")
    public ProductEntity replaceProduct(@RequestBody ProductEntity newProduct, @PathVariable Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(newProduct.getName());
                    product.setPrice(newProduct.getPrice());
                    return productRepository.save(product);
                })
                .orElseGet(() -> {
                    newProduct.setId(id);
                    return productRepository.save(newProduct);
                });
    }

    @Operation(summary = "Delete a product")
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }
}
