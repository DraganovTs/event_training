package org.homemade.product.service.repository;

import org.homemade.product.service.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {


    boolean existsByNameAndBrand(String name, String brand);

    List<Product> findAllByOwner_OwnerId(UUID ownerOwnerId);

    Optional<Product> findByNameAndOwner_OwnerId(String name, UUID ownerOwnerId);

    Product findByNameAndBrand(String name, String brand);
}
