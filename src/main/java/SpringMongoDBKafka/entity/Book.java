package SpringMongoDBKafka.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Setter
@Getter
@ToString
public class Book {

    @Id
    String id;
    private  String name;
    private String author;
    private int copies;

}
