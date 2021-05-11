package courseproject.javacheck.model.postgresqlModels;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Subject subject;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "text")
    private String text;

    @Column(name = "max_mark")
    private Integer maxMark;

    @Column(name = "class_count")
    private Integer classCount;

    @Column(name = "method_count")
    private Integer methodCount;

    public Task(Subject subject, String name, String text, Integer maxMark, Integer classCount, Integer methodCount) {
        this.subject = subject;
        this.name = name;
        this.text = text;
        this.maxMark = maxMark;
        this.classCount = classCount;
        this.methodCount = methodCount;
    }

    public Task() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getClassCount() {
        return classCount;
    }

    public void setClassCount(Integer classCount) {
        this.classCount = classCount;
    }

    public Integer getMethodCount() {
        return methodCount;
    }

    public void setMethodCount(Integer methodCount) {
        this.methodCount = methodCount;
    }

    public Integer getMaxMark() {
        return maxMark;
    }

    public void setMaxMark(Integer maxMark) {
        this.maxMark = maxMark;
    }
}
