package csd230.lab1.controllers;

import csd230.lab1.entities.InstrumentEntity;
import csd230.lab1.repositories.InstrumentEntityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Instrument REST API", description = "JSON API for managing instruments")
@RestController
@RequestMapping("/api/rest/instruments")
@CrossOrigin(origins = "*")
public class InstrumentRestController {
    private final InstrumentEntityRepository instrumentEntityRepository;

    public InstrumentRestController(InstrumentEntityRepository instrumentEntityRepository) {
        this.instrumentEntityRepository = instrumentEntityRepository;
    }

    @Operation(summary = "Get all instruments as JSON")
    @GetMapping
    public List<InstrumentEntity> all() {
        return instrumentEntityRepository.findAll();
    }

    @Operation(summary = "Get a single instrument by ID")
    @GetMapping("/{id}")
    public InstrumentEntity getInstrument(@PathVariable Long id) {
        return instrumentEntityRepository.findById(id)
                .orElseThrow(() -> new InstrumentNotFoundException(id));
    }

    @Operation(summary = "Create a new instrument")
    @PostMapping
    public InstrumentEntity newInstrument(@RequestBody InstrumentEntity newInstrument) {
        return instrumentEntityRepository.save(newInstrument);
    }

    @Operation(summary = "Update or Replace an instrument")
    @PutMapping("/{id}")
    public InstrumentEntity replaceInstrument(@RequestBody InstrumentEntity newInstrument, @PathVariable Long id) {
        return instrumentEntityRepository.findById(id)
                .map(instrument -> {
                    instrument.setBrand(newInstrument.getBrand());
                    instrument.setPrice(newInstrument.getPrice());
                    return instrumentEntityRepository.save(instrument);
                })
                .orElseGet(() -> {
                    newInstrument.setId(id);
                    return instrumentEntityRepository.save(newInstrument);
                });
    }

    @Operation(summary = "Delete an instrument")
    @DeleteMapping("/{id}")
    public void deleteInstrument(@PathVariable Long id) {
        instrumentEntityRepository.deleteById(id);
    }
}
