package co.istad.sengkim.ite3rdecommerce.features.file;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileUploadRepository extends JpaRepository<FileUpload,Long> {
    Optional<FileUpload> findByName(String name);
}
