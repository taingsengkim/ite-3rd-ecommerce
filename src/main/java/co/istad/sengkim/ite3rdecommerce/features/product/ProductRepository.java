package co.istad.sengkim.ite3rdecommerce.features.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    Boolean existsByName(String name);




}
