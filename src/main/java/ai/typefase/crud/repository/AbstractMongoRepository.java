package ai.typefase.crud.repository;

import ai.typefase.crud.entity.AbstractMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface AbstractMongoRepository<T extends AbstractMongoEntity, I extends Serializable> extends MongoRepository<T, I> {

    T findFirstById(String uuid);

}
