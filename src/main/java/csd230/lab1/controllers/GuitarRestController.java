package csd230.lab1.controllers;

import csd230.lab1.entities.GuitarEntity;
import csd230.lab1.repositories.GuitarEntityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Guitar REST API", description = "JSON API for managing guitars")
@RestController
@RequestMapping("/api/rest/guitars")
@CrossOrigin(origins = "*")
public class GuitarRestController {
    private final GuitarEntityRepository guitarRepository;

    public GuitarRestController(GuitarEntityRepository guitarEntityRepository) {
        this.guitarRepository = guitarEntityRepository;
    }

    @Operation(summary = "Get all guitars as JSON")
    @GetMapping
    public List<GuitarEntity> all() {return guitarRepository.findAll();}

    @Operation(summary = "Get a single guitar by ID")
    @GetMapping("/{id}")
    public GuitarEntity getGuitar(@PathVariable Long id) {
        return guitarRepository.findById(id)
                .orElseThrow(() -> new GuitarNotFoundException(id));
    }

    @Operation(summary = "Create a new guitar")
    @PostMapping
    public GuitarEntity newGuitar(@RequestBody GuitarEntity newGuitar) {
        return guitarRepository.save(newGuitar);
    }

    @Operation(summary = "Update or Replace a guitar")
    @PutMapping("/{id}")
    public GuitarEntity replaceGuitar(@RequestBody GuitarEntity newGuitar, @PathVariable Long id) {
        return guitarRepository.findById(id)
                .map(guitar -> {
                    guitar.setBrand(newGuitar.getBrand());
                    guitar.setPrice(newGuitar.getPrice());
                    return guitarRepository.save(guitar);
                })
                .orElseGet(() -> {
                    newGuitar.setId(id);
                    return guitarRepository.save(newGuitar);
                });
    }

    @Operation(summary = "Delete a guitar")
    @DeleteMapping("/{id}")
    public void deleteGuitar(@PathVariable Long id) {
        guitarRepository.deleteById(id);
    }
}
