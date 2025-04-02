package com.tientv.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Nationalized
    @Lob
    @Column(name = "product_name")
    private String productName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "price", precision = 18, scale = 2)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "discount_id", nullable = false)
    private Discount discount;

    @Column(name = "stock")
    private Integer stock;

    @Nationalized
    @Lob
    @Column(name = "description")
    private String description;

    @Nationalized
    @Column(name = "brand")
    private String brand;

    @ColumnDefault("getdate()")
    @Column(name = "created_date")
    private OffsetDateTime createdDate;

    @ColumnDefault("getdate()")
    @Column(name = "update_date")
    private OffsetDateTime updateDate;

    @ColumnDefault("1")
    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "product")
    private Set<Like> likes = new LinkedHashSet<>();

    // Corrected mappedBy to point to the 'products' field in OrderDetail
    @OneToOne(mappedBy = "products")
    private OrderDetail orderDetail;

    @OneToMany(mappedBy = "product")
    private Set<ProductImage> productImages = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<ProductReview> productReviews = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<ProductSpecification> productSpecifications = new LinkedHashSet<>();

}
