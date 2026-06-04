package co.istad.sengkim.ite3rdecommerce.repository;

import co.istad.sengkim.ite3rdecommerce.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine,Integer> {
}
