package org.homemade.product.service.repository;

import org.homemade.product.service.model.entity.Category;
import org.homemade.product.service.model.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, UUID> {
}
