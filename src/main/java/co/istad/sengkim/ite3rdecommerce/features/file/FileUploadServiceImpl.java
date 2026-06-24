package co.istad.sengkim.ite3rdecommerce.features.file;

import co.istad.sengkim.ite3rdecommerce.features.file.dto.FileUploadResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Value("${file.storage-location}")
    private String storageLocation;

    @Value("${file.base-URI}")
    private String baseUri;

    @Override
    public FileUploadResponse upload(MultipartFile multipartFile) {
        //-> Prepare file information

        //File name
        String name = UUID.randomUUID().toString();

        //myprofile.png
        String ext = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));

        name +=  ext; // new-unique-filename.ext

        //-> Create absolute path

        Path path = Paths.get(storageLocation + name);
        try {
            Files.copy(multipartFile.getInputStream(),path);
        }catch (IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"File has been failed to upload.");
        }
        return FileUploadResponse.builder()
                .name(name)
                .size(multipartFile.getSize())
                .mediaType(multipartFile.getContentType())
                .uri(baseUri+name).build();
    }

    @Override
    public List<FileUploadResponse> uploadMultiple(List<MultipartFile> files) {
        List<FileUploadResponse> responses = new ArrayList<>();
        for (MultipartFile file : files) {
            responses.add(upload(file));
        }
        return responses;
    }
    @Override
    public void delete(String name) {
        Path folder = Paths.get(storageLocation);
        try (var stream = Files.list(folder)) {
            Path targetPath = stream
                    .filter(path -> !Files.isDirectory(path))
                    .filter(path -> {
                        String filename = path.getFileName().toString();
                        return filename.startsWith(name + ".");
                    })
                    .findFirst()
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found: " + name));
            String filenameStr = targetPath.getFileName().toString();
            String ext = filenameStr.substring(filenameStr.lastIndexOf("."));
            Files.delete(targetPath);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete file: " + name, e);
        }
    }
}