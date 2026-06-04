package co.istad.sengkim.ite3rdecommerce.repository;

import co.istad.sengkim.ite3rdecommerce.model.Product;
import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {
}
