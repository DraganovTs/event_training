package org.homemade.product.service.repository;

import org.homemade.product.service.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    boolean existsByName(String name);

    List<Product> findAllByOwner_OwnerId(UUID ownerOwnerId);
}
