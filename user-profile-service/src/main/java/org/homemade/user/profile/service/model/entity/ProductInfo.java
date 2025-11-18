package org.homemade.user.profile.service.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "product_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductInfo {
    @Id
    @Column(name = "product_info_id")
    private UUID productId;
    @NotNull(message = "Product name must not be null")
    @Column(name = "product_name")
    private String name;
    @NotNull(message = "Brand name must not be null")
    @Column(name = "brand")
    private String brand;
    @NotNull(message = "Description must not be null")
    @Column(columnDefinition = "TEXT")
    private String description;
    @NotNull(message = "Category must not be null")
    @Column(name = "category")
    private String category;
    @Min(value = 0, message = "Price must be greater than or equal to zero")
    @Column(name = "price")
    private BigDecimal price;
    @Min(value = 0, message = "Units in stock must be greater than or equal to zero")
    @Column(name = "units_in_stock")
    private int unitsInStock;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id", nullable = false)
    private UserProfile userProfile;


}
