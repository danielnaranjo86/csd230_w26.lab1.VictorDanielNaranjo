package csd230.lab1.repositories;

import csd230.lab1.entities.InstrumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstrumentEntityRepository extends JpaRepository<InstrumentEntity, Long> {
    List<InstrumentEntity> findByBrand(String brand);
}
