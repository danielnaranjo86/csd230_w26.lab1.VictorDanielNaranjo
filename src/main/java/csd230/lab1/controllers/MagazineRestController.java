package csd230.lab1.controllers;

import csd230.lab1.entities.MagazineEntity;
import csd230.lab1.repositories.MagazineEntityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Magazine REST API", description = "JSON API for managing magazines")
@RestController
@RequestMapping("/api/rest/magazines")
@CrossOrigin(origins = "*")
public class MagazineRestController {
    private final MagazineEntityRepository magazineRepository;
    public MagazineRestController(MagazineEntityRepository magazineRepository) {this.magazineRepository = magazineRepository;}

    @Operation(summary = "Get all magazines as JSON")
    @GetMapping
    public List<MagazineEntity> all() {return magazineRepository.findAll();}

    @Operation(summary = "Get a single magazine by ID")
    @GetMapping("/{id}")
    public MagazineEntity getMagazine(@PathVariable Long id) {
        return magazineRepository.findById(id)
                .orElseThrow(() -> new MagazineNotFoundException(id));
    }

    @Operation(summary = "Create a new magazine")
    @PostMapping
    public MagazineEntity newMagazine(@RequestBody MagazineEntity newMagazine) {
        return magazineRepository.save(newMagazine);
    }

    @Operation(summary = "Update or Replace a magazine")
    @PutMapping("/{id}")
    public MagazineEntity replaceMagazine(@RequestBody MagazineEntity newMagazine, @PathVariable Long id) {
        return magazineRepository.findById(id)
                .map(magazine -> {
                    magazine.setTitle(newMagazine.getTitle());
                    magazine.setPrice(newMagazine.getPrice());
                    return magazineRepository.save(magazine);
                })
                .orElseGet(() -> {
                    newMagazine.setId(id);
                    return magazineRepository.save(newMagazine);
                });
    }

    @Operation(summary = "Delete a magazine")
    @DeleteMapping("/{id}")
    public void deleteMagazine(@PathVariable Long id) {
        magazineRepository.deleteById(id);
    }

}
