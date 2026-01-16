package csd230.lab1.repositories;

import csd230.lab1.entities.DrumKitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrumKitEntityRepository extends JpaRepository<DrumKitEntity, Long> {
    List<DrumKitEntity> findByBrandContainingIgnoreCase(String brand);
    List<DrumKitEntity> findByNumberOfPiecesGreaterThan(int pieces);
}
