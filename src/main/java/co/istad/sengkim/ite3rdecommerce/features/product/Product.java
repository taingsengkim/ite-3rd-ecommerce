package co.istad.sengkim.ite3rdecommerce.features.product;


import co.istad.sengkim.ite3rdecommerce.features.category.Category;
import co.istad.sengkim.ite3rdecommerce.features.order.orderline.OrderLine;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    private Double unitPrice;
    @Column(nullable = false)
    private Integer qty;

    @Column(nullable = false)
    private Boolean isAvailable;
    @Column(nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<OrderLine> orderLines;
}
