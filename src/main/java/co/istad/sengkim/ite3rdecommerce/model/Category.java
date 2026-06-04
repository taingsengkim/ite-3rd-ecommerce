package co.istad.sengkim.ite3rdecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true,nullable = false, length = 50)
    private String name;

    private String description;
    private String icon;
    @Column(nullable = false)
    private Boolean isDeleted;

    @ManyToOne // each category has one parent
    @JoinColumn(name = "parent_id")
    private Category parentCategory;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
