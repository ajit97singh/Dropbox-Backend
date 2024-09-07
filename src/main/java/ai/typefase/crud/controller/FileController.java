package ai.typefase.crud.controller;

import ai.typefase.crud.entity.FileMetadata;
import ai.typefase.crud.service.FileService;
import ai.typefase.crud.service.S3Service;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;
    @Autowired
    private S3Service s3Service;

    // CREATE API
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "metadata", required = false) String metadataJson) throws Exception {
        String fileId = fileService.uploadFile(file, metadataJson).getId();
        return ResponseEntity.ok(fileId);
    }

    // READ API
    @GetMapping("/{fileId}")
    public ResponseEntity<byte[]> getFile(@PathVariable String fileId) throws IOException {
        FileMetadata fileData = fileService.getFile(fileId);
        byte[] s3Object = s3Service.getFile(fileData.getFileUrl());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileData.getFileType()))
                .body(s3Object);
    }

    // Bulk Read
    @GetMapping("/")
    public ResponseEntity<List<FileMetadata>> getAllFiles() throws IOException {
        List<FileMetadata> fileData = fileService.getAllFiles();
        return ResponseEntity.ok().body(fileData);
    }

    // UPDATE API
    @PutMapping("/{fileId}")
    public ResponseEntity<FileMetadata> updateFile(
            @PathVariable String fileId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "metadata", required = false) String metadataJson,
            @RequestParam(value = "upsert", required = false, defaultValue = "false") boolean upsert) throws IOException {
        FileMetadata fileMetadata = fileService.updateFile(fileId, file, metadataJson, upsert);
        return ResponseEntity.ok(fileMetadata);
    }



    // DELETE API
    @DeleteMapping("/{fileId}")
    public ResponseEntity<Resource> deleteFile(@PathVariable String fileId) throws IOException {
        boolean success = fileService.deleteFile(fileId);
        return success? ResponseEntity.ok().build(): ResponseEntity.notFound().build();
    }



}

