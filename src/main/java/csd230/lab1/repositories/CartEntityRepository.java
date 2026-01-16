package csd230.lab1.repositories;

import csd230.lab1.entities.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartEntityRepository extends JpaRepository<CartEntity, Long> {
    List<CartEntity> findByProductsId(Long productId);

}
