package hu.codecool.elastic.elastic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Entity;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "article", type = "news")
public class News implements Serializable {

    @Id
    private String id;

    private String header;

    private String content;

    private String created_at;

    public News(String header, String content, String created_at) {
        this.header = header;
        this.content = content;
        this.created_at = created_at;
    }
}
