package org.homemade.user.profile.service.repository;

import org.homemade.user.profile.service.model.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductInfoRepository extends JpaRepository<ProductInfo, UUID> {
}
