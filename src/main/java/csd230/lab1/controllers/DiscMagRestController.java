package csd230.lab1.controllers;

import csd230.lab1.entities.DiscMagEntity;
import csd230.lab1.repositories.DiscMagEntityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "DiscMag REST API", description = "JSON API for managing disc magazines")
@RestController
@RequestMapping("/api/rest/discmags")
@CrossOrigin(origins = "*")
public class DiscMagRestController {
    private final DiscMagEntityRepository discMagRepository;

    public DiscMagRestController(DiscMagEntityRepository discMagRepository) {this.discMagRepository = discMagRepository;}

    @Operation(summary = "Get all disc magazines as JSON")
    @GetMapping
    public List<DiscMagEntity> all() {return discMagRepository.findAll();}

    @Operation(summary = "Get a single disc magazine by ID")
    @GetMapping("/{id}")
    public DiscMagEntity getDiscMag(@PathVariable Long id) {
        return discMagRepository.findById(id)
                .orElseThrow(() -> new DiscMagNotFoundException(id));
    }

    @Operation(summary = "Create a new disc magazine")
    @PostMapping
    public DiscMagEntity newDiscMag(@RequestBody DiscMagEntity newDiscMag) {
        return discMagRepository.save(newDiscMag);
    }

    @Operation(summary = "Update or Replace a disc magazine")
    @PutMapping("/{id}")
    public Optional<DiscMagEntity> replaceDiscMag(@RequestBody DiscMagEntity newDiscMag, @PathVariable Long id) {
        return Optional.of(discMagRepository.findById(id)
                .map(discMag -> {
                    discMag.setTitle(newDiscMag.getTitle());
                    discMag.setPrice(newDiscMag.getPrice());
                    discMag.setCopies(newDiscMag.getCopies());
                    return discMagRepository.save(discMag);
                })
                .orElseGet(() -> {
                    newDiscMag.setId(id);
                    return discMagRepository.save(newDiscMag);
                }));
    }

    @Operation(summary = "Delete a disc magazine")
    @DeleteMapping("/{id}")
    public void deleteDiscMag(@PathVariable Long id) {
        discMagRepository.deleteById(id);
    }
}
