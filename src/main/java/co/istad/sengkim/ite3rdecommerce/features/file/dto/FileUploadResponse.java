package co.istad.sengkim.ite3rdecommerce.features.file.dto;

import lombok.Builder;

@Builder
public record FileUploadResponse(
        String name,
        String caption,
        Long size,
        String extension,
        String mediaType,
        String uri
) {
}
