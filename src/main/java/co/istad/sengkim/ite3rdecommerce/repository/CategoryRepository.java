package co.istad.sengkim.ite3rdecommerce.repository;

import co.istad.sengkim.ite3rdecommerce.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    Page<Category> findAllByIsDeleted(Boolean isDeletedBoolean, Pageable pageable);
    boolean existsByName(@NotBlank @Size(max = 50) String name);

    List<Category> findAllByIsDeletedAndParentCategory(Boolean isDeleted,Category parentCategory);

    List<Category> findByParentCategory(Category parentCategory);
}
