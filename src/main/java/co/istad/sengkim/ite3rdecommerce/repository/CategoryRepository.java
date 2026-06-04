package co.istad.sengkim.ite3rdecommerce.repository;

import co.istad.sengkim.ite3rdecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
