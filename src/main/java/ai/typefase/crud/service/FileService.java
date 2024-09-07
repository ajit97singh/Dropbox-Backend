package ai.typefase.crud.service;

import ai.typefase.crud.entity.FileMetadata;
import ai.typefase.crud.repository.FileMetadataRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@Log4j2
public class FileService {

    @Autowired
    private S3Service s3Service;

    @Autowired
    private FileMetadataRepository fileMetadataRepository;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String bucketName = "dropbox";

    public FileMetadata uploadFile(MultipartFile fileRaw, String metadataJson) throws IOException {
        byte[] file = fileRaw.getBytes();
        String fileId = UUID.randomUUID().toString();
        Map<String, Object> jsonMap;
        try {
            jsonMap = objectMapper.readValue(metadataJson, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String s3Url = s3Service.uploadFile(file, fileId, jsonMap);

        FileMetadata metadata = FileMetadata.builder()
                .fileType(String.valueOf(jsonMap.get("fileType")))
                .fileName(String.valueOf(jsonMap.get("fileName")))
                .fileUrl(s3Url)
                .size(file.length)
                .createdAt(LocalDateTime.now())
                .build();

        return fileMetadataRepository.save(metadata);
    }

    public FileMetadata getFile(String fileId) throws IOException {
        FileMetadata file = fileMetadataRepository.findFirstById(fileId);
        if (Objects.isNull(file)) {
            throw new FileNotFoundException(fileId);
        }
        return file;
    }

    public FileMetadata updateFile(String fileId, MultipartFile fileRaw, String metadataJson, Boolean upsert) throws IOException {
        // get existing file
        FileMetadata existingFile = fileMetadataRepository.findFirstById(fileId);
        if (Objects.isNull(existingFile) && !upsert) {
            throw new FileNotFoundException(fileId);
        } else if (Objects.isNull(existingFile)) {
                uploadFile(fileRaw, metadataJson);
                // returning
                return fileMetadataRepository.findFirstById(fileId);
        }
        Map<String, Object> jsonMap;
        try {
            jsonMap = objectMapper.readValue(metadataJson, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        byte[] file = fileRaw.getBytes();
        String s3Url = s3Service.uploadFile(file, fileId, jsonMap);
        existingFile.setFileType(String.valueOf(jsonMap.get("fileType")));
        existingFile.setFileName(String.valueOf(jsonMap.get("fileName")));
        existingFile.setFileUrl(s3Url);
        existingFile.setSize(file.length);
        existingFile.setCreatedAt(LocalDateTime.now());
        return fileMetadataRepository.save(existingFile);
    }

    public boolean deleteFile(String fileId) throws IOException {
        try {
            fileMetadataRepository.deleteById(fileId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<FileMetadata> getAllFiles() {
        return fileMetadataRepository.findAll();
    }
}

