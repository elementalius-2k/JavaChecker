package courseproject.javacheck.model.postgresqlModels;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private User teacher;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    public Subject(User teacher, String name, String description) {
        this.teacher = teacher;
        this.name = name;
        this.description = description;
    }

    public Subject() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
