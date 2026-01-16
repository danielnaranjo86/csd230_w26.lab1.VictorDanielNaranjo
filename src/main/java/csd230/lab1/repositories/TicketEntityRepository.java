package csd230.lab1.repositories;

import csd230.lab1.entities.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketEntityRepository extends JpaRepository<TicketEntity, Long> {

    // "LIKE" style query (based on description)
    List<TicketEntity> findByDescriptionLike(String pattern);
    // usage: "%concert%"

    // Useful derived queries
    List<TicketEntity> findByDescriptionContainingIgnoreCase(String text);

    // Price range derived query (works because field name is "price")
    List<TicketEntity> findByPriceBetween(double min, double max);

    // Custom @Query (meets lab requirement too)
    @Query("SELECT t FROM TicketEntity t WHERE t.price >= :min")
    List<TicketEntity> findTicketsMinPrice(@Param("min") double min);
}
