package ai.typefase.crud.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractMongoEntity implements Serializable {

    @Id
    protected String id;

    protected String uuid;

    protected Date createdAt;

    protected String createdBy;

    @LastModifiedDate
    protected Date updatedAt;

    protected String updatedBy;


}
