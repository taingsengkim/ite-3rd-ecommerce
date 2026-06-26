package co.istad.sengkim.ite3rdecommerce.features.file;

import co.istad.sengkim.ite3rdecommerce.features.file.dto.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {
    private  final  FileUploadRepository fileUploadRepository;
    private final FileUploadMapper fileUploadMapper;

    @Value("${file.storage-location}")
    private String storageLocation;



    @Override
    public FileUploadResponse findByName(String name) {
        return fileUploadRepository.findByName(name).map(fileUploadMapper::mapFileUploadToFileUploadResponse)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"File not found : "+name));
    }
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

    @Override
    public Page<FileUploadResponse> findAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(Sort.Direction.DESC,"id"));
        Page<FileUpload> fileUploadResponses = fileUploadRepository.findAll(pageable);
        return fileUploadResponses.map(fileUploadMapper::mapFileUploadToFileUploadResponse);
    }

    @Override
    public FileUploadResponse upload(MultipartFile multipartFile) {
        //-> Prepare file information

        //File name
        String name = UUID.randomUUID().toString();

        //myprofile.png
        String ext = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        //-> Create absolute path
        Path path = Paths.get(storageLocation + name+"."+ext);
        try {
            Files.copy(multipartFile.getInputStream(),path);
        }catch (IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"File has been failed to upload.");
        }

        //Save information file into db
        FileUpload fileUpload = new FileUpload();
        fileUpload.setName(name);
        fileUpload.setExtension(ext);
        fileUpload.setCaption("ISTAD - Advanced IT Institute in Cambodia");
        fileUpload.setSize(multipartFile.getSize());
        fileUpload.setMediaType(multipartFile.getContentType());
        fileUploadRepository.save(fileUpload);
        return fileUploadMapper.mapFileUploadToFileUploadResponse(fileUpload);
    }

    @Override
    public List<FileUploadResponse> uploadMultiple(List<MultipartFile> files) {

        return files.stream().map(this::upload).toList();
    }
    @Override
    public void delete(String name) {
        FileUpload fileUpload = fileUploadRepository.findByName(name)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"File not found!"));
        fileUploadRepository.delete(fileUpload);

        Path path = Paths.get(storageLocation + fileUpload.getName() + "." + fileUpload.getExtension());
        try {
            boolean isExisted = Files.deleteIfExists(path);
            if(!isExisted){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"File has not been deleted");
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete file: " + name, e);
        }
    }
}