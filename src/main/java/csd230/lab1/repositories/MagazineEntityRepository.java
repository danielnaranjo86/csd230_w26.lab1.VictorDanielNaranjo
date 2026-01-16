package csd230.lab1.repositories;

import csd230.lab1.entities.MagazineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MagazineEntityRepository extends JpaRepository<MagazineEntity, Long> {

    // Inherited from PublicationEntity
    List<MagazineEntity> findByTitleContainingIgnoreCase(String title);

    // Like query (lab requirement style)
    List<MagazineEntity> findByTitleLike(String pattern); // "%tech%"

    // Magazine-specific field
    List<MagazineEntity> findByOrderQtyGreaterThan(int qty);

    // Magazine-specific field
    List<MagazineEntity> findByCurrentIssueAfter(LocalDateTime dateTime);

    // Custom @Query (valid because price is in PublicationEntity)
    @Query("SELECT m FROM MagazineEntity m WHERE m.price BETWEEN :min AND :max")
    List<MagazineEntity> findMagazinesInPriceRange(@Param("min") double min, @Param("max") double max);
}
