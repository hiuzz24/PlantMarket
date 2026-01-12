package pacman.plantmarket.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "category_name",length = 50,nullable = false,unique = true)
    private String categoryName;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Column(name = "description",length = 255)
    private String description;

    @OneToMany(mappedBy = "category",
                fetch = FetchType.LAZY)
    private List<Product> products;
}
