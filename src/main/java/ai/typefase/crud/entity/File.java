package ai.typefase.crud.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Id;
import java.util.Date;
import java.util.Map;


@Getter
@Setter
public class File {

    @Id
    private String Id;
    private String url;
    private Map<String, Object> metaData;
    private Date createdAt;

}
