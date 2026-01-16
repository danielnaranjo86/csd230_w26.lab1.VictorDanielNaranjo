package csd230.lab1.repositories;

import csd230.lab1.entities.GuitarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuitarEntityRepository extends JpaRepository<GuitarEntity, Long> {
    List<GuitarEntity> findByBrandContainingIgnoreCase(String brand);
    List<GuitarEntity> findByNumberOfStrings(int strings);
}
