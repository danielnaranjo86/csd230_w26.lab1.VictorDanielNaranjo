package csd230.lab1.repositories;

import csd230.lab1.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookEntityRepository extends JpaRepository<BookEntity, Long> {

    BookEntity findById(long id);

    // If PublicationEntity has title:
    List<BookEntity> findByTitleLike(String pattern);
    List<BookEntity> findByTitleContainingIgnoreCase(String title);

    // This one is guaranteed because BookEntity has author:
    List<BookEntity> findByAuthorContainingIgnoreCase(String author);

    // Use only if PublicationEntity has "price"
    @Query("SELECT b FROM BookEntity b WHERE b.price BETWEEN :min AND :max")
    List<BookEntity> findBooksInPriceRange(@Param("min") double min, @Param("max") double max);
}
