package org.homemade.product.service.repository;

import org.homemade.product.service.model.entity.Category;
import org.homemade.product.service.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
}
