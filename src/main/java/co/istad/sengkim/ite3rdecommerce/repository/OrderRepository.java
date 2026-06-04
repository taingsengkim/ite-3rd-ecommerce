package co.istad.sengkim.ite3rdecommerce.repository;

import co.istad.sengkim.ite3rdecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
