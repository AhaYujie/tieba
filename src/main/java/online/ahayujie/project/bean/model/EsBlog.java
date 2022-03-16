package online.ahayujie.project.bean.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
@Document(indexName = "blog", shards = 1, replicas = 0)
public class EsBlog {
    @Id
    private Long id;

    @Field(type = FieldType.Date)
    private Date updateTime;

    @Field(type = FieldType.Date)
    private Date createTime;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String content;

    private Long userId;

    @Field(type = FieldType.Keyword)
    private String username;

    private Long sectionId;

    @Field(type = FieldType.Keyword)
    private String sectionName;

    @Field(type = FieldType.Text)
    private String tag;
}
