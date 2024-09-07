package ai.typefase.crud.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Document(collection = "file_metadata")
public class FileMetadata extends AbstractMongoEntity{

    @Id
    private String id;
    private String fileName;
    private String fileUrl;
    private long size;
    private String fileType;
    private LocalDateTime createdAt;
}

