package co.istad.sengkim.ite3rdecommerce.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.AnyDiscriminatorImplicitValues;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false,unique = true)
    private String slug;
    @Column(nullable = false, length = 500)
    private String description;
    @Column(nullable = false)
    private String thumbnail;
    @Column(nullable = false)
    private Integer unit_price;
    @Column(nullable = false)
    private Integer qty;

    @Column(nullable = false)
    private Boolean is_available;
    @Column(nullable = false)
    private Boolean is_deleted;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<OrderLine> orderLines;
}
