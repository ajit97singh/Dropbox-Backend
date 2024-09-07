package ai.typefase.crud.repository;

import ai.typefase.crud.entity.FileMetadata;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMetadataRepository extends AbstractMongoRepository<FileMetadata, String>  {

}
