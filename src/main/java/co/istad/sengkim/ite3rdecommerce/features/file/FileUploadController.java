package co.istad.sengkim.ite3rdecommerce.features.file;

import co.istad.sengkim.ite3rdecommerce.features.file.dto.FileUploadResponse;
import jakarta.servlet.ServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileUploadResponse upload(@RequestParam MultipartFile file){
        return fileUploadService.upload(file);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<FileUploadResponse> uploadMultiple(@RequestParam List<MultipartFile> files){
        return fileUploadService.uploadMultiple(files);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{name}")
    public void deleteByName(@PathVariable String name){
        fileUploadService.delete(name);
    }

    @GetMapping
    public Page<FileUploadResponse> getAll(
            @RequestParam(defaultValue = "0",required = false) int pageNumber,
            @RequestParam(defaultValue = "25",required = false) int pageSize, ServletRequest servletRequest){
        return fileUploadService.findAll(pageNumber,pageSize);
    }
    @GetMapping("/{name}")
    public FileUploadResponse getByName(@PathVariable String name){
        return fileUploadService.findByName(name);
    }


}
