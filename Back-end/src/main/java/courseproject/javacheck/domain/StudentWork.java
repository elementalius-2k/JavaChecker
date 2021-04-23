package courseproject.javacheck.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_works")
public class StudentWork {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "task_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference(value = "work-task")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference(value = "work-user")
    private User user;

    @Column(name = "path")
    private String path;

    @Column(name = "system_review")
    private String systemReview;

    @Column(name = "teacher_review")
    private String teacherReview;

    @Column(name = "originality")
    private Integer originality;

    @Column(name = "mark")
    private Integer mark;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @JsonIgnore
    @Column(name = "local_path")
    private String localPath;

    public StudentWork(Task task, User user, String path, String systemReview, String teacherReview,
                       Integer originality, Integer mark, LocalDateTime dateTime, String localPath) {
        this.user = user;
        this.task = task;
        this.path = path;
        this.systemReview = systemReview;
        this.teacherReview = teacherReview;
        this.originality = originality;
        this.mark = mark;
        this.dateTime = dateTime;
        this.localPath = localPath;
    }

    public StudentWork() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSystemReview() {
        return systemReview;
    }

    public void setSystemReview(String systemReview) {
        this.systemReview = systemReview;
    }

    public String getTeacherReview() {
        return teacherReview;
    }

    public void setTeacherReview(String teacherReview) {
        this.teacherReview = teacherReview;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Integer getOriginality() {
        return originality;
    }

    public void setOriginality(Integer originality) {
        this.originality = originality;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }
}
