package csd230.lab1.controllers;

import csd230.lab1.entities.DrumKitEntity;
import csd230.lab1.repositories.DrumKitEntityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "DrumKit REST API", description = "JSON API for managing drum kits")
@RestController
@RequestMapping("/api/rest/drumkits")
@CrossOrigin(origins = "*")
public class DrumKitRestController {
    private final DrumKitEntityRepository drumKitRepository;

    public DrumKitRestController(DrumKitEntityRepository drumKitRepository) {this.drumKitRepository = drumKitRepository;}

    @Operation(summary = "Get all drum kits as JSON")
    @GetMapping
    public List<DrumKitEntity> all() {return drumKitRepository.findAll();}

    @Operation (summary = "Get a single drum kit by ID")
    @GetMapping("/{id}")
    public DrumKitEntity getDrumKit(@PathVariable Long id) {
        return drumKitRepository.findById(id)
                .orElseThrow(() -> new DrumKitNotFoundException(id));
    }

    @Operation(summary = "Create a new drum kit")
    @PostMapping
    public DrumKitEntity newDrumKit(@RequestBody DrumKitEntity newDrumKit) {
        return drumKitRepository.save(newDrumKit);
    }

    @Operation(summary = "Update or Replace a drum kit")
    @PutMapping("/{id}")
    public DrumKitEntity replaceDrumKit(@RequestBody DrumKitEntity newDrumKit, @PathVariable Long id) {
        return drumKitRepository.findById(id)
                .map(drumKit -> {
                    drumKit.setBrand(newDrumKit.getBrand());
                    drumKit.setPrice(newDrumKit.getPrice());
                    return drumKitRepository.save(drumKit);
                })
                .orElseGet(() -> {
                    newDrumKit.setId(id);
                    return drumKitRepository.save(newDrumKit);
                });
    }

    @Operation(summary = "Delete a drum kit")
    @DeleteMapping("/{id}")
    public void deleteDrumKit(@PathVariable Long id) {
        drumKitRepository.deleteById(id);
    }
}
