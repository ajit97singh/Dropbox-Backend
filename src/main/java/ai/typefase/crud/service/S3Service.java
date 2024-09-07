package ai.typefase.crud.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
public class S3Service {
    private final AmazonS3 s3client;

    private static final String bucketName = "dropbox";

    public S3Service() {
        this.s3client = AmazonS3ClientBuilder.standard().build();
    }

    public String uploadFile(byte[] file, String fileId, Map<String, Object> metadataJson) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        for (Map.Entry<String, Object> entry : metadataJson.entrySet()) {
            objectMetadata.addUserMetadata(entry.getKey(), entry.getValue().toString());
        }
        objectMetadata.setContentLength(file.length);
        objectMetadata.setContentType(fileId);
        PutObjectRequest request = new PutObjectRequest(bucketName, fileId, new ByteArrayInputStream(file), objectMetadata);
        s3client.putObject(request);
        return s3client.getUrl(bucketName, fileId).toString();
    }

    public byte[] getFile(String path) throws IOException {
        GetObjectRequest request = new GetObjectRequest(bucketName, path);
        S3Object file = s3client.getObject(request);
        if (Objects.isNull(file)) {
            throw new FileNotFoundException(path);
        }
        return file.getObjectContent().readAllBytes();
    }
}

