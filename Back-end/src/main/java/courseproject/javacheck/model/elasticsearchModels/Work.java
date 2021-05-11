package courseproject.javacheck.model.elasticsearchModels;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Class representing the student's work for internal checks
 * Not using for Front-end
 */
@Document(indexName = "work_index")
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Field(type = FieldType.Integer, name = "work_id")
    private Integer workId;

    @Field(type = FieldType.Text, name = "full_name")
    private String fullName;

    @Field(type = FieldType.Text, name = "report")
    private String report;
    
    @Field(type = FieldType.Text, name = "structure")
    private String structure;
    
    @Field(type = FieldType.Text, name = "all_names")
    private String allNames;

    @Field(type = FieldType.Text, name = "local_path")
    private String localPath;
    
    public Work() {}
    
    public Work(Integer workId, String fullName, String report, String structure, String allNames, String localPath) {
        this.workId = workId;
        this.fullName = fullName;
        this.report = report;
        this.structure = structure;
        this.allNames = allNames;
        this.localPath = localPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getWorkId() {
        return workId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public String getAllNames() {
        return allNames;
    }

    public void setAllNames(String allNames) {
        this.allNames = allNames;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }
}
