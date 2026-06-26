package co.istad.sengkim.ite3rdecommerce.features.file;

import co.istad.sengkim.ite3rdecommerce.features.file.dto.FileUploadResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileUploadMapper {
    @Value("${file.base-URI}")
    private String baseUri;
    public FileUploadResponse mapFileUploadToFileUploadResponse(FileUpload fileUpload){
        return FileUploadResponse.builder()
                .name(fileUpload.getName())
                .size(fileUpload.getSize())
                .extension(fileUpload.getExtension())
                .caption(fileUpload.getCaption())
                .mediaType(fileUpload.getMediaType())
                .uri(baseUri+fileUpload.getName()+"."+fileUpload.getExtension()).build();
    }

}
