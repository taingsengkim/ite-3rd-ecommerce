package co.istad.sengkim.ite3rdecommerce.features.file;

import co.istad.sengkim.ite3rdecommerce.features.file.dto.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadService {
    FileUploadResponse upload(MultipartFile multipartFile);

    List<FileUploadResponse> uploadMultiple(List<MultipartFile> files);

    void delete(String name);
}
