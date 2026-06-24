package co.istad.sengkim.ite3rdecommerce.features.order.orderline;

import co.istad.sengkim.ite3rdecommerce.features.order.Order;
import co.istad.sengkim.ite3rdecommerce.features.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "order_lines")
@Entity
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;


    private Integer qty;
    private Double unitPrice;
}
