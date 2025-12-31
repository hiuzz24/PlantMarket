package pacman.plantmarket.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "name",nullable = false,length = 50)
    private String name;

    @Column(name = "description",length = 255)
    private String description;

    @Column(name = "price",nullable = false,precision = 10,scale = 2)
    private BigDecimal price;

    @Column(name = "is_best_seller",nullable = false)
    private Boolean isBestSeller = false;

    @Column(name = "stock_quantity",nullable = false)
    private Integer stockQuantity;

    @Column(name = "image_url",length = 500,nullable = false)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty_level",nullable = false)
    private DifficultyLevel difficultyLevel;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted",nullable = false)
    private Boolean isDeleted = false;

    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        updatedAt = LocalDateTime.now();
    }

}
